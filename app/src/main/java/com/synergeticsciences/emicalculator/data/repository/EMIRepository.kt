package com.synergeticsciences.emicalculator.data.repository

import com.synergeticsciences.emicalculator.data.local.EMICalculationDao
import com.synergeticsciences.emicalculator.data.model.EMICalculation
import kotlinx.coroutines.flow.Flow

class EMIRepository(private val dao: EMICalculationDao) {
    
    val allCalculations: Flow<List<EMICalculation>> = dao.getAllCalculations()
    
    fun getRecentCalculations(limit: Int = 10): Flow<List<EMICalculation>> {
        return dao.getRecentCalculations(limit)
    }
    
    suspend fun insert(calculation: EMICalculation): Long {
        return dao.insert(calculation)
    }
    
    suspend fun delete(calculation: EMICalculation) {
        dao.delete(calculation)
    }
    
    suspend fun deleteAll() {
        dao.deleteAll()
    }
    
    fun getCalculationCount(): Flow<Int> {
        return dao.getCalculationCount()
    }
}

