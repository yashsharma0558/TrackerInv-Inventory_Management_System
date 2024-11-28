package com.dev.trackerinv.ui.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
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
}