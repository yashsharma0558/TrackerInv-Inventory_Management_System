package com.dev.trackerinv.domain.mapper

import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.domain.model.BarChartData
object ChartDataMapper {

    fun mapToBarChartData(
        sales: List<Sale>,
        purchases: List<Purchase>
    ): BarChartData {
        val salesQuantities = sales.map { it.quantity }
        val purchaseQuantities = purchases.map { it.quantity }
        val labels = sales.map { it.date } // Assuming you want to use dates as labels

        return BarChartData(
            salesQuantities = salesQuantities,
            purchaseQuantities = purchaseQuantities,
            labels = labels
        )
    }
}
