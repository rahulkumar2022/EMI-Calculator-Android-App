package com.synergeticsciences.emicalculator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "emi_calculations")
data class EMICalculation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val loanAmount: Double,
    val interestRate: Double,
    val tenureMonths: Int,
    val emiAmount: Double,
    val totalInterest: Double,
    val totalPayable: Double,
    val timestamp: Long = Date().time
)

