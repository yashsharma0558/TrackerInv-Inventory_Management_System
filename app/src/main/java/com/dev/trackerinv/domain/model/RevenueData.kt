package com.dev.trackerinv.domain.model

data class RevenueData(
    val month: String,
    val totalAmount: Double
)
data class RevenueSaleData(
    val month: String,
    val totalSales: Double
)
data class RevenuePurchaseData(
    val month: String,
    val totalPurchases: Double
)
