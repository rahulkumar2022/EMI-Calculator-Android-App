package com.synergeticsciences.emicalculator.utils

import com.synergeticsciences.emicalculator.data.model.Currency
import com.synergeticsciences.emicalculator.data.model.CurrencyFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object InputFormatter {
    
    /**
     * Formats input value with thousand separators based on currency format
     */
    fun formatInput(input: String, currency: Currency): String {
        if (input.isEmpty()) return ""
        
        // Remove all non-digit characters
        val digitsOnly = input.filter { it.isDigit() }
        if (digitsOnly.isEmpty()) return ""
        
        // Convert to number
        val number = digitsOnly.toLongOrNull() ?: return input
        
        return when (currency.format) {
            CurrencyFormat.INDIAN -> formatIndianStyle(number)
            CurrencyFormat.WESTERN -> formatWesternStyle(number)
            CurrencyFormat.NONE -> digitsOnly
        }
    }
    
    /**
     * Formats decimal input (for interest rate)
     */
    fun formatDecimalInput(input: String): String {
        if (input.isEmpty()) return ""
        
        // Allow digits and one decimal point
        val filtered = input.filter { it.isDigit() || it == '.' }
        
        // Ensure only one decimal point
        val parts = filtered.split(".")
        return if (parts.size > 2) {
            parts[0] + "." + parts.drop(1).joinToString("")
        } else {
            filtered
        }
    }
    
    /**
     * Removes formatting to get raw number
     */
    fun removeFormatting(formatted: String): String {
        return formatted.filter { it.isDigit() || it == '.' }
    }
    
    /**
     * Gets raw value from formatted input
     */
    fun getRawValue(formatted: String): String {
        return formatted.filter { it.isDigit() }
    }
    
    private fun formatIndianStyle(number: Long): String {
        val symbols = DecimalFormatSymbols(Locale.US).apply {
            groupingSeparator = ','
        }
        
        // Indian numbering: xx,xx,xxx
        val formatter = DecimalFormat("#,##,##0", symbols)
        return formatter.format(number)
    }
    
    private fun formatWesternStyle(number: Long): String {
        val symbols = DecimalFormatSymbols(Locale.US).apply {
            groupingSeparator = ','
        }
        
        // Western numbering: xxx,xxx
        val formatter = DecimalFormat("#,##0", symbols)
        return formatter.format(number)
    }
}

