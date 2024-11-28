package com.dev.trackerinv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.trackerinv.domain.usecase.FilterPurchasesByDateUseCase
import com.dev.trackerinv.domain.usecase.FilterSalesByDateUseCase
import com.dev.trackerinv.domain.usecase.GenerateReportUseCase

class ReportViewModelFactory(
    private val filterSalesByDateUseCase: FilterSalesByDateUseCase,
    private val filterPurchasesByDateUseCase: FilterPurchasesByDateUseCase,
    private val generateReportUseCase: GenerateReportUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            return ReportViewModel(filterSalesByDateUseCase, filterPurchasesByDateUseCase, generateReportUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}