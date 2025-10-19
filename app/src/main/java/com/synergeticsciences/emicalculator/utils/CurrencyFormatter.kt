package com.synergeticsciences.emicalculator.utils

import com.synergeticsciences.emicalculator.data.model.Currency
import com.synergeticsciences.emicalculator.data.model.CurrencyFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object CurrencyFormatter {
    
    /**
     * Formats amount with currency symbol
     */
    fun formatCurrency(amount: Double, currency: Currency): String {
        val formattedAmount = when (currency.format) {
            CurrencyFormat.INDIAN -> formatIndianStyle(amount)
            CurrencyFormat.WESTERN -> formatWesternStyle(amount)
            CurrencyFormat.NONE -> String.format(Locale.US, "%.2f", amount)
        }
        
        return "${currency.symbol}$formattedAmount"
    }
    
    /**
     * Formats amount with appropriate suffix (K, L, Cr, M, B)
     */
    fun formatAmount(amount: Double, currency: Currency): String {
        return when (currency.format) {
            CurrencyFormat.INDIAN -> formatIndianAmount(amount)
            CurrencyFormat.WESTERN -> formatWesternAmount(amount)
            CurrencyFormat.NONE -> String.format(Locale.US, "%.2f", amount)
        }
    }
    
    private fun formatIndianStyle(amount: Double): String {
        val symbols = DecimalFormatSymbols(Locale.US).apply {
            groupingSeparator = ','
            decimalSeparator = '.'
        }
        
        val formatter = DecimalFormat("#,##,##0.00", symbols)
        return formatter.format(amount)
    }
    
    private fun formatWesternStyle(amount: Double): String {
        val symbols = DecimalFormatSymbols(Locale.US).apply {
            groupingSeparator = ','
            decimalSeparator = '.'
        }
        
        val formatter = DecimalFormat("#,##0.00", symbols)
        return formatter.format(amount)
    }
    
    private fun formatIndianAmount(amount: Double): String {
        return when {
            amount >= 10000000 -> String.format(Locale.US, "%.2f Cr", amount / 10000000)
            amount >= 100000 -> String.format(Locale.US, "%.2f L", amount / 100000)
            amount >= 1000 -> String.format(Locale.US, "%.2f K", amount / 1000)
            else -> String.format(Locale.US, "%.2f", amount)
        }
    }
    
    private fun formatWesternAmount(amount: Double): String {
        return when {
            amount >= 1000000000 -> String.format(Locale.US, "%.2f B", amount / 1000000000)
            amount >= 1000000 -> String.format(Locale.US, "%.2f M", amount / 1000000)
            amount >= 1000 -> String.format(Locale.US, "%.2f K", amount / 1000)
            else -> String.format(Locale.US, "%.2f", amount)
        }
    }
}

