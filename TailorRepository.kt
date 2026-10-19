package com.bombaywala.tailor.bharuch.data.repository

import com.bombaywala.tailor.bharuch.data.dao.CustomerDao
import com.bombaywala.tailor.bharuch.data.dao.MeasurementDao
import com.bombaywala.tailor.bharuch.data.model.Customer
import com.bombaywala.tailor.bharuch.data.model.MeasurementRecord
import kotlinx.coroutines.flow.Flow

class TailorRepository(
    private val customerDao: CustomerDao,
    private val measurementDao: MeasurementDao
) {
    val allCustomers: Flow<List<Customer>> = customerDao.getAllCustomers()

    fun searchCustomers(query: String): Flow<List<Customer>> {
        return if (query.isBlank()) {
            customerDao.getAllCustomers()
        } else {
            customerDao.searchCustomers(query)
        }
    }

    suspend fun getCustomerById(id: Long): Customer? = customerDao.getCustomerById(id)

    suspend fun insertCustomer(customer: Customer): Long = customerDao.insertCustomer(customer)

    suspend fun updateCustomer(customer: Customer) = customerDao.updateCustomer(customer)

    suspend fun deleteCustomer(customer: Customer) = customerDao.deleteCustomer(customer)

    fun getMeasurementsForCustomer(customerId: Long): Flow<List<MeasurementRecord>> =
        measurementDao.getMeasurementsForCustomer(customerId)

    suspend fun insertMeasurement(record: MeasurementRecord): Long =
        measurementDao.insertMeasurement(record)

    suspend fun updateMeasurement(record: MeasurementRecord) =
        measurementDao.updateMeasurement(record)

    suspend fun deleteMeasurement(record: MeasurementRecord) =
        measurementDao.deleteMeasurement(record)
}
