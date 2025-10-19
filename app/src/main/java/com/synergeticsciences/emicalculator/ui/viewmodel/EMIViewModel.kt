package com.synergeticsciences.emicalculator.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.synergeticsciences.emicalculator.data.local.AppDatabase
import com.synergeticsciences.emicalculator.data.model.Currency
import com.synergeticsciences.emicalculator.data.model.EMICalculation
import com.synergeticsciences.emicalculator.data.model.EMIResult
import com.synergeticsciences.emicalculator.data.model.SupportedCurrencies
import com.synergeticsciences.emicalculator.data.preferences.PreferencesManager
import com.synergeticsciences.emicalculator.data.repository.EMIRepository
import com.synergeticsciences.emicalculator.utils.EMICalculator
import com.synergeticsciences.emicalculator.utils.InputFormatter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EMIViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: EMIRepository
    private val preferencesManager: PreferencesManager = PreferencesManager(application)
    
    // Currency state
    val selectedCurrency: StateFlow<Currency> = preferencesManager.selectedCurrencyCode
        .map { code -> SupportedCurrencies.getByCode(code) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SupportedCurrencies.INR
        )
    
    // Input fields state (formatted for display)
    private val _loanAmount = MutableStateFlow("")
    val loanAmount: StateFlow<String> = _loanAmount.asStateFlow()
    
    // Raw values (without formatting, for calculation)
    private val _loanAmountRaw = MutableStateFlow("")
    
    private val _interestRate = MutableStateFlow("")
    val interestRate: StateFlow<String> = _interestRate.asStateFlow()
    
    private val _tenureYears = MutableStateFlow("")
    val tenureYears: StateFlow<String> = _tenureYears.asStateFlow()
    
    // Calculation result
    private val _emiResult = MutableStateFlow<EMIResult?>(null)
    val emiResult: StateFlow<EMIResult?> = _emiResult.asStateFlow()
    
    // Navigation event for one-time navigation to result screen
    private val _navigateToResult = MutableSharedFlow<Unit>()
    val navigateToResult: SharedFlow<Unit> = _navigateToResult.asSharedFlow()
    
    // Calculation history
    val calculationHistory: StateFlow<List<EMICalculation>>
    val calculationCount: StateFlow<Int>
    
    // Error state
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        val dao = AppDatabase.getDatabase(application).emiCalculationDao()
        repository = EMIRepository(dao)
        
        calculationHistory = repository.getRecentCalculations(20)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
        
        calculationCount = repository.getCalculationCount()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0
            )
        
        // Update EMICalculator with selected currency
        viewModelScope.launch {
            selectedCurrency.collect { currency ->
                EMICalculator.setCurrency(currency)
            }
        }
    }
    
    fun updateLoanAmount(value: String) {
        // Store raw value (digits only)
        val rawValue = InputFormatter.getRawValue(value)
        _loanAmountRaw.value = rawValue
        
        // Format for display
        val formatted = InputFormatter.formatInput(value, selectedCurrency.value)
        _loanAmount.value = formatted
        
        _errorMessage.value = null
    }
    
    fun updateInterestRate(value: String) {
        // Allow decimal input for interest rate
        val formatted = InputFormatter.formatDecimalInput(value)
        _interestRate.value = formatted
        _errorMessage.value = null
    }
    
    fun updateTenureYears(value: String) {
        _tenureYears.value = value
        _errorMessage.value = null
    }
    
    fun calculateEMI() {
        // Use raw value for calculation
        val amountNullable = _loanAmountRaw.value.toDoubleOrNull()
        val rateNullable = _interestRate.value.toDoubleOrNull()
        val yearsNullable = _tenureYears.value.toIntOrNull()
        
        when {
            amountNullable == null || amountNullable <= 0 -> {
                _errorMessage.value = "Please enter a valid loan amount"
                return
            }
            rateNullable == null || rateNullable <= 0 || rateNullable > 100 -> {
                _errorMessage.value = "Please enter a valid interest rate (0-100)"
                return
            }
            yearsNullable == null || yearsNullable <= 0 || yearsNullable > 30 -> {
                _errorMessage.value = "Please enter a valid tenure (1-30 years)"
                return
            }
        }
        
        // After null checks, we know these are non-null
        val amount: Double = amountNullable!!
        val rate: Double = rateNullable!!
        val years: Int = yearsNullable!!
        val tenureMonths = years * 12
        
        try {
            val result = EMICalculator.calculateEMI(
                principalAmount = amount,
                annualInterestRate = rate,
                tenureMonths = tenureMonths
            )
            
            _emiResult.value = result
            
            // Emit navigation event
            viewModelScope.launch {
                _navigateToResult.emit(Unit)
            }
            
            // Save to history
            saveCalculation(amount, rate, tenureMonths, result)
            
        } catch (e: Exception) {
            _errorMessage.value = "Error calculating EMI: ${e.message}"
        }
    }
    
    private fun saveCalculation(
        amount: Double,
        rate: Double,
        tenureMonths: Int,
        result: EMIResult
    ) {
        viewModelScope.launch {
            try {
                val calculation = EMICalculation(
                    loanAmount = amount,
                    interestRate = rate,
                    tenureMonths = tenureMonths,
                    emiAmount = result.emiAmount,
                    totalInterest = result.totalInterest,
                    totalPayable = result.totalPayable
                )
                repository.insert(calculation)
            } catch (e: Exception) {
                // Silently fail - history is not critical
            }
        }
    }
    
    fun clearResult() {
        _emiResult.value = null
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
    
    fun clearHistory() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
    
    fun deleteCalculation(calculation: EMICalculation) {
        viewModelScope.launch {
            repository.delete(calculation)
        }
    }
    
    fun resetInputs() {
        _loanAmount.value = ""
        _loanAmountRaw.value = ""
        _interestRate.value = ""
        _tenureYears.value = ""
        _emiResult.value = null
        _errorMessage.value = null
    }
    
    fun setCurrency(currency: Currency) {
        viewModelScope.launch {
            preferencesManager.setCurrency(currency.code)
        }
    }
}

