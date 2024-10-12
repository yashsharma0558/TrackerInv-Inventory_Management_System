package com.dev.trackerinv.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.trackerinv.data.model.GST

@Entity(tableName = "sale")
data class SaleEntity(
    @PrimaryKey val id: String,
    val image: String,
    val platform: String,
    val date: String,
    val stockId: String,
    val productName: String,
    val cusName: String,
    val cusPh: String,
    val quantity: Int,
    val sp: Double,
    val gst: GST,
    val deliveryChannel: String,
    val deliveryAndOtherExpenses: Double
)
