package com.dev.trackerinv.domain.usecase

import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.data.repository.SaleRepository

class FilterSalesByDateUseCase(private val salesRepository: SaleRepository) {
    suspend operator fun invoke(startDate: String, endDate: String): List<Sale> {
        return salesRepository.getSalesByDateRange(startDate, endDate)
    }
}