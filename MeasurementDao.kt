package com.bombaywala.tailor.bharuch.data.dao

import androidx.room.*
import com.bombaywala.tailor.bharuch.data.model.MeasurementRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {
    @Query("SELECT * FROM measurements WHERE customerId = :customerId ORDER BY createdAt DESC")
    fun getMeasurementsForCustomer(customerId: Long): Flow<List<MeasurementRecord>>

    @Query("SELECT * FROM measurements WHERE id = :id LIMIT 1")
    suspend fun getMeasurementById(id: Long): MeasurementRecord?

    @Query("SELECT * FROM measurements ORDER BY createdAt DESC")
    fun getAllMeasurements(): Flow<List<MeasurementRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasurement(record: MeasurementRecord): Long

    @Update
    suspend fun updateMeasurement(record: MeasurementRecord)

    @Delete
    suspend fun deleteMeasurement(record: MeasurementRecord)

    @Query("DELETE FROM measurements")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(measurements: List<MeasurementRecord>)
}
