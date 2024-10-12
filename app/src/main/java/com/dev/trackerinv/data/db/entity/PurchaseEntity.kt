package com.dev.trackerinv.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.trackerinv.data.model.GST

@Entity(tableName = "purchase")
data class PurchaseEntity(
    @PrimaryKey val id: String,
    val image: String,
    val invNo: String,
    val invDate: String,
    val supName: String,
    val supPh: String,
    val quantity: Int,
    val gst: GST,
    val pricePerUnit: Double
)
