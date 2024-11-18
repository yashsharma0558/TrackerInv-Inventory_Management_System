package com.dev.trackerinv.domain.usecase

import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.repository.PurchaseRepository

class FilterPurchasesByDateUseCase(private val purchaseRepository: PurchaseRepository) {
    suspend operator fun invoke(startDate: String, endDate: String): List<Purchase> {
        return purchaseRepository.getPurchasesByDateRange(startDate, endDate)
    }
}