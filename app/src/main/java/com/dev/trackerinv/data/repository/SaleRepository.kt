package com.dev.trackerinv.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.data.api.ApiService
import com.dev.trackerinv.data.db.AppDatabase
import com.dev.trackerinv.data.db.entity.SaleEntity
import com.dev.trackerinv.domain.mapper.toDomainModel
import com.dev.trackerinv.domain.mapper.toEntity

class SaleRepository(private val apiService: ApiService, database: AppDatabase) {


    private val saleDao = database.saleDao()


    suspend fun syncAllSales() {
        val sales = apiService.getAllSales().map { it.toEntity() }
        // Insert data into Room database
        saleDao.insertAllSales(sales)
    }

    fun getAllSalesFromRoom(): LiveData<List<Sale>> {
        return saleDao.getAllSales()
            .map { it -> it.map { it.toDomainModel() } } // Fetches LiveData from the DAO
    }

    suspend fun getSalesByDateRange(startDate: String, endDate: String): List<Sale> {
        return saleDao.getSalesByDateRange(startDate, endDate)
            .map { it.toDomainModel() }
    }

    suspend fun getSaleById(id: String): Sale? {
        // First, check the Room database
        var sale = saleDao.getSaleById(id)
        if (sale == null) {
            // If not found in Room, fetch from API
            val newSale = apiService.getSaleById(id)
            if (newSale.isSuccessful) {
                println("fetched from api")
                sale = newSale.body()?.toEntity()
                saleDao.insertSale(sale!!)
                return sale.toDomainModel()
            } else {
                println("api failed")
                return null
            }
        }
        println("fetched from room")
        return sale.toDomainModel()
    }


    suspend fun addSale(sale: Sale, showToast: ((String) -> (Unit))) {
        val existingSale = saleDao.getSaleById(sale.id)
        println("REPOSITORY $existingSale")
        if (existingSale == null) {
            // Add to both Room and Remote Database if sale does not exist
            if (apiService.createSale(sale).isSuccessful) {
                saleDao.insertSale(sale.toEntity())
                showToast("Sale Added")
                println("API SALE SUCCESSFUL")
            } else {
                Log.e("SaleRepository", "addSale: " + apiService.createSale(sale).message())
                showToast("Sale Not Added")
                println("API SALE FAILED")
            }
        } else {
            showToast("Sale Already Exists")
            println("SALE ALREADY EXISTS")
        }


    }

}

