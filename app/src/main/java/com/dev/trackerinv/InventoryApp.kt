package com.dev.trackerinv

import android.app.Application
import androidx.room.Room
import com.dev.trackerinv.data.api.ApiService
import com.dev.trackerinv.data.api.RetrofitClient
import com.dev.trackerinv.data.db.AppDatabase

class InventoryApp : Application() {
    lateinit var database: AppDatabase
    lateinit var apiService: ApiService

    override fun onCreate() {
        super.onCreate()

        apiService = RetrofitClient.instance


        database = AppDatabase.getDatabase(applicationContext)
    }
}
