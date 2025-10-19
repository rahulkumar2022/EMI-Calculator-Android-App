package com.synergeticsciences.emicalculator.data.local

import androidx.room.*
import com.synergeticsciences.emicalculator.data.model.EMICalculation
import kotlinx.coroutines.flow.Flow

@Dao
interface EMICalculationDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(calculation: EMICalculation): Long
    
    @Query("SELECT * FROM emi_calculations ORDER BY timestamp DESC")
    fun getAllCalculations(): Flow<List<EMICalculation>>
    
    @Query("SELECT * FROM emi_calculations ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentCalculations(limit: Int = 10): Flow<List<EMICalculation>>
    
    @Query("SELECT * FROM emi_calculations WHERE id = :id")
    suspend fun getCalculationById(id: Long): EMICalculation?
    
    @Delete
    suspend fun delete(calculation: EMICalculation)
    
    @Query("DELETE FROM emi_calculations")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM emi_calculations")
    fun getCalculationCount(): Flow<Int>
}

