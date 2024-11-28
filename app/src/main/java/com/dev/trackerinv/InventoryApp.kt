package com.dev.trackerinv

import android.app.Application
import androidx.room.Room
import com.dev.trackerinv.data.api.ApiService
import com.dev.trackerinv.data.api.RetrofitClient
import com.dev.trackerinv.data.db.AppDatabase
import com.dev.trackerinv.data.repository.PurchaseRepository
import com.dev.trackerinv.data.repository.SaleRepository
import com.dev.trackerinv.domain.usecase.FilterPurchasesByDateUseCase
import com.dev.trackerinv.domain.usecase.FilterSalesByDateUseCase
import com.dev.trackerinv.domain.usecase.GenerateReportUseCase
import com.dev.trackerinv.ui.viewmodel.InventoryViewModelFactory
import com.dev.trackerinv.ui.viewmodel.PurchaseViewModel
import com.dev.trackerinv.ui.viewmodel.ReportViewModel
import com.dev.trackerinv.ui.viewmodel.ReportViewModelFactory
import com.dev.trackerinv.ui.viewmodel.SaleViewModel

class InventoryApp : Application() {
    lateinit var database: AppDatabase
    lateinit var apiService: ApiService
    lateinit var saleViewModel: SaleViewModel
    lateinit var purchaseViewModel: PurchaseViewModel
    lateinit var reportViewModel: ReportViewModel

    override fun onCreate() {
        super.onCreate()

        apiService = RetrofitClient.instance
        database = AppDatabase.getDatabase(applicationContext)

        // Initialize the sale view model
        val saleRepository = SaleRepository(apiService, database)
        val saleFactory = InventoryViewModelFactory(SaleViewModel::class.java, saleRepository)
        saleViewModel = saleFactory.create(SaleViewModel::class.java)

        // Initialize the purchase view model
        val purchaseRepository = PurchaseRepository(apiService, database)
        val purchaseFactory =
            InventoryViewModelFactory(PurchaseViewModel::class.java, purchaseRepository)
        purchaseViewModel = purchaseFactory.create(PurchaseViewModel::class.java)

        // Initialize the purchase view model
        val reportUseCase = GenerateReportUseCase()
        val reportUseCase1 = FilterSalesByDateUseCase(saleRepository)
        val reportUseCase2 = FilterPurchasesByDateUseCase(purchaseRepository)
        val reportFactory =
            ReportViewModelFactory(reportUseCase1, reportUseCase2, reportUseCase)
        reportViewModel = reportFactory.create(ReportViewModel::class.java)

    }
}
