package com.bombaywala.tailor.bharuch.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "measurements",
    foreignKeys = [
        ForeignKey(
            entity = Customer::class,
            parentColumns = ["id"],
            childColumns = ["customerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["customerId"])]
)
data class MeasurementRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerId: Long,
    val date: String,
    val garmentType: String,
    val gender: String, // "ladies" or "gents"
    val unit: String = "inches", // "inches" or "cm"

    // Ladies Measurements
    val bust: String = "",
    val waist: String = "",
    val hip: String = "",
    val shoulder: String = "",
    val sleeveLength: String = "",
    val blouseLength: String = "",
    val kameezLength: String = "",
    val salwarLength: String = "",
    val neckDesignNotes: String = "",

    // Gents Measurements
    val chest: String = "",
    val shirtLength: String = "",
    val trouserLength: String = "",
    val neck: String = "",

    // Common
    val specialNotes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
