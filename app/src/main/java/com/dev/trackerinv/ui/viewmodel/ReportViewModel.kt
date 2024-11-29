package com.dev.trackerinv.ui.viewmodel


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.domain.mapper.ChartDataMapper
import com.dev.trackerinv.domain.model.BarChartData
import com.dev.trackerinv.domain.model.ChartType
import com.dev.trackerinv.domain.model.PieChartData
import com.dev.trackerinv.domain.usecase.FilterPurchasesByDateUseCase
import com.dev.trackerinv.domain.usecase.FilterSalesByDateUseCase
import com.dev.trackerinv.domain.usecase.GenerateReportUseCase
import kotlinx.coroutines.launch
import java.io.File

class ReportViewModel(
    private val filterSalesByDateUseCase: FilterSalesByDateUseCase,
    private val filterPurchasesByDateUseCase: FilterPurchasesByDateUseCase,
    private val generateReportUseCase: GenerateReportUseCase

) : ViewModel() {
    val chartType: List<ChartType> = ChartType.entries
    fun generateReport(
        startDate: String,
        endDate: String,
        context: Context,
        onSuccess: (File) -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Filter data by date
                val sales: List<Sale> = filterSalesByDateUseCase(startDate, endDate)
                val purchases: List<Purchase> = filterPurchasesByDateUseCase(startDate, endDate)

                // Generate the report
                val reportFile = generateReportUseCase(sales, purchases, context)
                onSuccess(reportFile)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun fetchBarChartData(
        startDate: String,
        endDate: String,
        onSuccess: (BarChartData) -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val sales = filterSalesByDateUseCase(startDate, endDate)
                val purchases = filterPurchasesByDateUseCase(startDate, endDate)

                // Map data to BarChartData
                val barChartData = ChartDataMapper.mapToBarChartData(sales, purchases)

                onSuccess(barChartData)
            } catch (exception: Exception) {
                Log.d("TAG", "fetchBarChartData: ${exception.message}")
                onError(exception)
            }
        }
    }

    fun fetchPieChartData(
        startDate: String,
        endDate: String,
        onSuccess: (PieChartData) -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val sales = filterSalesByDateUseCase(startDate, endDate)
                val purchases = filterPurchasesByDateUseCase(startDate, endDate)

                // Sum up quantities for sales and purchases
                val totalSales = sales.sumOf { it.quantity }
                val totalPurchases = purchases.sumOf { it.quantity }

                onSuccess(PieChartData(totalSales, totalPurchases))
            } catch (exception: Exception) {
                Log.e("PieChartError", "Error fetching pie chart data: ${exception.message}")
                onError(exception)
            }


        }
    }
}