package com.dev.trackerinv.data.repository

import com.dev.trackerinv.data.db.dao.PurchaseDao
import com.dev.trackerinv.data.db.dao.SaleDao

class ReportRepository(
    private val salesDao: SaleDao,
    private val purchaseDao: PurchaseDao
) {
    suspend fun getTotalSales(startDate: String, endDate: String): Double {
        return salesDao.getTotalSales(startDate, endDate)
    }

    suspend fun getTotalSales(): Double {
        return salesDao.getTotalSales()
    }

    suspend fun getTotalPurchases(startDate: String, endDate: String): Double {
        return purchaseDao.getTotalPurchases(startDate, endDate)
    }

    suspend fun getTotalPurchases(): Double {
        return purchaseDao.getTotalPurchases()
    }

}
