package com.dev.trackerinv.domain.model

data class RevenueTrend(
    val month: String,
    val totalSales: Double,
    val totalPurchases: Double,
    val netRevenue: Double
)