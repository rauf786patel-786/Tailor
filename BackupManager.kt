package com.bombaywala.tailor.bharuch.data.backup

import android.content.Context
import android.net.Uri
import com.bombaywala.tailor.bharuch.data.database.TailorDatabase
import com.bombaywala.tailor.bharuch.data.model.Customer
import com.bombaywala.tailor.bharuch.data.model.MeasurementRecord
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class BackupPayload(
    val appName: String = "Bombaywala Tailor Bharuch",
    val version: String = "1.0",
    val exportDate: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),
    val shop: String = "Bombaywala Tailor - Station Road, Bharuch",
    val customers: List<Customer>,
    val measurements: List<MeasurementRecord>
)

class BackupManager(private val context: Context, private val database: TailorDatabase) {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    suspend fun exportBackup(uri: Uri): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val customers = database.customerDao().getAllCustomers().first()
            val measurements = database.measurementDao().getAllMeasurements().first()
            val payload = BackupPayload(
                customers = customers,
                measurements = measurements
            )
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                OutputStreamWriter(outputStream).use { writer ->
                    gson.toJson(payload, writer)
                }
            }
            Result.success(customers.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun restoreBackup(uri: Uri): Result<Pair<Int, Int>> = withContext(Dispatchers.IO) {
        try {
            val payload: BackupPayload = context.contentResolver.openInputStream(uri)?.use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    gson.fromJson(reader, BackupPayload::class.java)
                }
            } ?: return@withContext Result.failure(Exception("Could not read backup file"))

            database.customerDao().insertAll(payload.customers)
            database.measurementDao().insertAll(payload.measurements)

            Result.success(Pair(payload.customers.size, payload.measurements.size))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
