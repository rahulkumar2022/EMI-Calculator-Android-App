package com.synergeticsciences.emicalculator.data.model

data class Currency(
    val code: String,
    val name: String,
    val symbol: String,
    val format: CurrencyFormat
)

enum class CurrencyFormat {
    INDIAN,      // Uses Lakhs and Crores (e.g., 1,00,000)
    WESTERN,     // Uses Thousands and Millions (e.g., 100,000)
    NONE         // No special formatting
}

object SupportedCurrencies {
    val INR = Currency(
        code = "INR",
        name = "Indian Rupee",
        symbol = "₹",
        format = CurrencyFormat.INDIAN
    )
    
    val USD = Currency(
        code = "USD",
        name = "US Dollar",
        symbol = "$",
        format = CurrencyFormat.WESTERN
    )
    
    val EUR = Currency(
        code = "EUR",
        name = "Euro",
        symbol = "€",
        format = CurrencyFormat.WESTERN
    )
    
    val GBP = Currency(
        code = "GBP",
        name = "British Pound",
        symbol = "£",
        format = CurrencyFormat.WESTERN
    )
    
    val JPY = Currency(
        code = "JPY",
        name = "Japanese Yen",
        symbol = "¥",
        format = CurrencyFormat.WESTERN
    )
    
    val AUD = Currency(
        code = "AUD",
        name = "Australian Dollar",
        symbol = "A$",
        format = CurrencyFormat.WESTERN
    )
    
    val CAD = Currency(
        code = "CAD",
        name = "Canadian Dollar",
        symbol = "C$",
        format = CurrencyFormat.WESTERN
    )
    
    val CHF = Currency(
        code = "CHF",
        name = "Swiss Franc",
        symbol = "CHF",
        format = CurrencyFormat.WESTERN
    )
    
    val CNY = Currency(
        code = "CNY",
        name = "Chinese Yuan",
        symbol = "¥",
        format = CurrencyFormat.WESTERN
    )
    
    val AED = Currency(
        code = "AED",
        name = "UAE Dirham",
        symbol = "د.إ",
        format = CurrencyFormat.WESTERN
    )
    
    val SAR = Currency(
        code = "SAR",
        name = "Saudi Riyal",
        symbol = "﷼",
        format = CurrencyFormat.WESTERN
    )
    
    val SGD = Currency(
        code = "SGD",
        name = "Singapore Dollar",
        symbol = "S$",
        format = CurrencyFormat.WESTERN
    )
    
    val ALL = listOf(INR, USD, EUR, GBP, JPY, AUD, CAD, CHF, CNY, AED, SAR, SGD)
    
    fun getByCode(code: String): Currency {
        return ALL.find { it.code == code } ?: INR
    }
}

