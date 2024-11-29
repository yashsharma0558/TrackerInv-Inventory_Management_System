package com.dev.trackerinv.domain.model

data class BarChartData(
    val salesQuantities: List<Int>,        // Quantities from Sale
    val purchaseQuantities: List<Int>,    // Quantities from Purchase
    val labels: List<String>              // Labels for the x-axis (e.g., dates or categories)
)
