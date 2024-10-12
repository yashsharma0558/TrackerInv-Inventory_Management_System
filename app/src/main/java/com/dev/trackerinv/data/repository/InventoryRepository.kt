package com.dev.trackerinv.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.data.api.ApiService
import com.dev.trackerinv.data.db.AppDatabase
import com.dev.trackerinv.domain.mapper.toDomainModel
import com.dev.trackerinv.domain.mapper.toEntity

class InventoryRepository(private val apiService: ApiService, private val database: AppDatabase) {


    private val saleDao = database.saleDao()
    private val purchaseDao = database.purchaseDao()

    suspend fun syncAllPurchases() {
        val purchases = apiService.getAllPurchases().map { it.toEntity() }
        // Insert data into Room database
        purchaseDao.insertAllPurchases(purchases)
    }

    suspend fun syncAllSales() {
        val sales = apiService.getAllSales().map { it.toEntity() }
        // Insert data into Room database
        saleDao.insertAllSales(sales)
    }

    fun getAllPurchasesFromRoom(): LiveData<List<Purchase>> {
        return purchaseDao.getAllPurchases().map { it -> it.map { it.toDomainModel() } } // Fetches LiveData from the DAO
    }

    fun getAllSalesFromRoom(): LiveData<List<Sale>> {
        return saleDao.getAllSales().map { it -> it.map { it.toDomainModel() } } // Fetches LiveData from the DAO
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
            }
            else{
                println("api failed")
                return null
            }
        }
        println("fetched from room")
        return sale.toDomainModel()
    }

    suspend fun getPurchaseById(id: String): Purchase? {
        var purchase = purchaseDao.getPurchaseById(id)
        if (purchase == null) {
            val newPurchase = apiService.getPurchaseById(id)
            if (newPurchase.isSuccessful) {
                println("fetched from api")
                purchase = newPurchase.body()?.toEntity()
                purchaseDao.insertPurchase(purchase!!)
                return purchase.toDomainModel()
            }
            else{
                println("api failed")
                return null
            }
        }
        println("fetched from room")
        return purchase.toDomainModel()
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
                showToast("Sale Not Added")
                println("API SALE FAILED")
            }
        } else {
            showToast("Sale Already Exists")
            println("SALE ALREADY EXISTS")
        }


    }

    suspend fun addPurchase(purchase: Purchase, showToast: ((String) -> (Unit))) {
        val existingPurchase = purchaseDao.getPurchaseById(purchase.id)

        if (existingPurchase == null) {

            if (apiService.createPurchase(purchase).isSuccessful) {
                purchaseDao.insertPurchase(purchase.toEntity())
                showToast("Purchase Added")
                println("API PURCHASE SUCCESSFUL")
            } else {
                showToast("Purchase Not Added")
                println("API PURCHASE FAILED")
            }
        } else {
            showToast("Purchase Already Exists")
            println("PURCHASE ALREADY EXISTS")
        }

    }
}

