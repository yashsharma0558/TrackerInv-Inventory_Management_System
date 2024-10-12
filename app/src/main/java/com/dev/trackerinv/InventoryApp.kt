package com.dev.trackerinv

import android.app.Application
import androidx.room.Room
import com.dev.trackerinv.data.api.ApiService
import com.dev.trackerinv.data.api.RetrofitClient
import com.dev.trackerinv.data.db.AppDatabase
import com.dev.trackerinv.data.repository.InventoryRepository
import com.dev.trackerinv.ui.viewmodel.InventoryViewModel
import com.dev.trackerinv.ui.viewmodel.InventoryViewModelFactory

class InventoryApp : Application() {
    lateinit var database: AppDatabase
    lateinit var apiService: ApiService
    lateinit var viewModel: InventoryViewModel

    override fun onCreate() {
        super.onCreate()

        apiService = RetrofitClient.instance
        database = AppDatabase.getDatabase(applicationContext)

        val repository = InventoryRepository(apiService, database)
        val factory = InventoryViewModelFactory(repository)
        viewModel = factory.create(InventoryViewModel::class.java)

    }
}
