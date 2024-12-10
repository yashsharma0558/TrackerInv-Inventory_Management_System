package com.dev.trackerinv.ui.viewmodel


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.data.repository.ReportRepository
import com.dev.trackerinv.domain.mapper.ChartDataMapper
import com.dev.trackerinv.domain.model.BarChartData
import com.dev.trackerinv.domain.model.ChartType
import com.dev.trackerinv.domain.model.FinancialSummary
import com.dev.trackerinv.domain.model.PieChartData
import com.dev.trackerinv.domain.model.RevenueTrend
import com.dev.trackerinv.domain.usecase.FilterPurchasesByDateUseCase
import com.dev.trackerinv.domain.usecase.FilterSalesByDateUseCase
import com.dev.trackerinv.domain.usecase.GenerateReportUseCase
import kotlinx.coroutines.launch
import java.io.File

class ReportViewModel(
    private val filterSalesByDateUseCase: FilterSalesByDateUseCase,
    private val filterPurchasesByDateUseCase: FilterPurchasesByDateUseCase,
    private val generateReportUseCase: GenerateReportUseCase,
    private val repository: ReportRepository
) : ViewModel() {


    val chartType: List<ChartType> = ChartType.entries

    private val _financialSummary = MutableLiveData<FinancialSummary>()
    val financialSummary: LiveData<FinancialSummary> get() = _financialSummary

    private val _revenueTrends = MutableLiveData<List<RevenueTrend>>()
    val revenueTrends: LiveData<List<RevenueTrend>> = _revenueTrends

    fun fetchFinancialSummary() {
        viewModelScope.launch {
            val summary = calculateFinancialSummary()
            _financialSummary.postValue(summary)
        }
    }



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

    suspend fun calculateFinancialSummary(): FinancialSummary {
        val totalSales = repository.getTotalSales()
        val totalPurchases = repository.getTotalPurchases()
        val profit = totalSales - totalPurchases
        return FinancialSummary(totalSales, totalPurchases, profit)
    }

    suspend fun calculateFinancialSummaryInRange(startDate: String, endDate: String): FinancialSummary {
        val totalSales = repository.getTotalSales(startDate, endDate)
        val totalPurchases = repository.getTotalPurchases(startDate, endDate)
        val profit = totalSales - totalPurchases
        return FinancialSummary(totalSales, totalPurchases, profit)
    }

    fun fetchRevenueTrends() {
        viewModelScope.launch {
            val trends = mutableListOf<RevenueTrend>()
            val monthMap = mapOf(
                "01" to "January",
                "02" to "February",
                "03" to "March",
                "04" to "April",
                "05" to "May",
                "06" to "June",
                "07" to "July",
                "08" to "August",
                "09" to "September",
                "10" to "October",
                "11" to "November",
                "12" to "December"
            )
            for(i in 1..12){
                var month = i.toString()
                if(i < 10){
                    month = "0$i"
                }
                val curSale = filterSalesByDateUseCase("2024-$month-01", "2024-$month-31")
                val sale = curSale.sumOf { it.quantity * it.sp }
                val purchase = filterPurchasesByDateUseCase("2024-$month-01", "2024-$month-31").sumOf { it.quantity * it.price_per_unit }
                val netRevenue = sale - purchase
                val trend = RevenueTrend(monthMap[month]!!, sale, purchase, netRevenue)
                Log.d("THEVALIS","month "+month+ " sale "+sale + " purchase "+purchase )
                trends.add(trend)
            }
            _revenueTrends.postValue(trends)
//            Log.d("THEVALIS", trends.toString())
        }
    }



}