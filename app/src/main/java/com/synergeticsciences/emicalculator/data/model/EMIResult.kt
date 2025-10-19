package com.synergeticsciences.emicalculator.data.model

data class EMIResult(
    val emiAmount: Double,
    val totalInterest: Double,
    val totalPayable: Double,
    val principalAmount: Double,
    val amortizationSchedule: List<AmortizationEntry>
)

data class AmortizationEntry(
    val month: Int,
    val emiAmount: Double,
    val principalComponent: Double,
    val interestComponent: Double,
    val outstandingPrincipal: Double
)

