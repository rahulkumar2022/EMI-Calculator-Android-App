package com.synergeticsciences.emicalculator.utils

import com.synergeticsciences.emicalculator.data.model.AmortizationEntry
import com.synergeticsciences.emicalculator.data.model.Currency
import com.synergeticsciences.emicalculator.data.model.EMIResult
import com.synergeticsciences.emicalculator.data.model.SupportedCurrencies
import kotlin.math.pow

object EMICalculator {
    
    /**
     * Calculates EMI using the formula:
     * EMI = [P x R x (1+R)^N] / [(1+R)^N - 1]
     * where:
     * P = Principal loan amount
     * R = Monthly interest rate (annual rate / 12 / 100)
     * N = Loan tenure in months
     */
    fun calculateEMI(
        principalAmount: Double,
        annualInterestRate: Double,
        tenureMonths: Int
    ): EMIResult {
        // Convert annual interest rate to monthly rate
        val monthlyInterestRate = annualInterestRate / 12 / 100
        
        // Calculate EMI
        val emi = if (monthlyInterestRate == 0.0) {
            principalAmount / tenureMonths
        } else {
            val temp = (1 + monthlyInterestRate).pow(tenureMonths.toDouble())
            (principalAmount * monthlyInterestRate * temp) / (temp - 1)
        }
        
        val totalPayable = emi * tenureMonths
        val totalInterest = totalPayable - principalAmount
        
        // Generate amortization schedule
        val amortizationSchedule = generateAmortizationSchedule(
            principalAmount,
            monthlyInterestRate,
            emi,
            tenureMonths
        )
        
        return EMIResult(
            emiAmount = emi,
            totalInterest = totalInterest,
            totalPayable = totalPayable,
            principalAmount = principalAmount,
            amortizationSchedule = amortizationSchedule
        )
    }
    
    private fun generateAmortizationSchedule(
        principalAmount: Double,
        monthlyInterestRate: Double,
        emi: Double,
        tenureMonths: Int
    ): List<AmortizationEntry> {
        val schedule = mutableListOf<AmortizationEntry>()
        var outstandingPrincipal = principalAmount
        
        for (month in 1..tenureMonths) {
            val interestComponent = outstandingPrincipal * monthlyInterestRate
            val principalComponent = emi - interestComponent
            outstandingPrincipal -= principalComponent
            
            // Ensure outstanding principal doesn't go negative due to rounding
            if (outstandingPrincipal < 0) outstandingPrincipal = 0.0
            
            schedule.add(
                AmortizationEntry(
                    month = month,
                    emiAmount = emi,
                    principalComponent = principalComponent,
                    interestComponent = interestComponent,
                    outstandingPrincipal = outstandingPrincipal
                )
            )
        }
        
        return schedule
    }
    
    // Default currency for backward compatibility
    private var currentCurrency: Currency = SupportedCurrencies.INR
    
    fun setCurrency(currency: Currency) {
        currentCurrency = currency
    }
    
    fun getCurrency(): Currency = currentCurrency
    
    /**
     * Formats a number to currency format (backward compatible)
     */
    fun formatCurrency(amount: Double): String {
        return CurrencyFormatter.formatCurrency(amount, currentCurrency)
    }
    
    /**
     * Formats a number to currency format with specific currency
     */
    fun formatCurrency(amount: Double, currency: Currency): String {
        return CurrencyFormatter.formatCurrency(amount, currency)
    }
    
    /**
     * Formats a number to display with appropriate suffix (backward compatible)
     */
    fun formatAmount(amount: Double): String {
        return CurrencyFormatter.formatAmount(amount, currentCurrency)
    }
    
    /**
     * Formats a number to display with appropriate suffix for specific currency
     */
    fun formatAmount(amount: Double, currency: Currency): String {
        return CurrencyFormatter.formatAmount(amount, currency)
    }
}

