package com.bombaywala.tailor.bharuch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bombaywala.tailor.bharuch.data.dao.CustomerDao
import com.bombaywala.tailor.bharuch.data.dao.MeasurementDao
import com.bombaywala.tailor.bharuch.data.model.Customer
import com.bombaywala.tailor.bharuch.data.model.MeasurementRecord

@Database(
    entities = [Customer::class, MeasurementRecord::class],
    version = 1,
    exportSchema = false
)
abstract class TailorDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun measurementDao(): MeasurementDao

    companion object {
        @Volatile
        private var INSTANCE: TailorDatabase? = null

        fun getDatabase(context: Context): TailorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TailorDatabase::class.java,
                    "bombaywala_tailor.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
