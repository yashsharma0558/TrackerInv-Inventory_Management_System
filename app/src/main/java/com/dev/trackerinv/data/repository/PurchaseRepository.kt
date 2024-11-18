package com.dev.trackerinv.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.data.api.ApiService
import com.dev.trackerinv.data.db.AppDatabase
import com.dev.trackerinv.domain.mapper.toDomainModel
import com.dev.trackerinv.domain.mapper.toEntity

class PurchaseRepository(private val apiService: ApiService, database: AppDatabase) {


    private val purchaseDao = database.purchaseDao()

    suspend fun syncAllPurchases() {
        val purchases = apiService.getAllPurchases().map { it.toEntity() }
        // Insert data into Room database
        purchaseDao.insertAllPurchases(purchases)
    }

    fun getAllPurchasesFromRoom(): LiveData<List<Purchase>> {
        return purchaseDao.getAllPurchases()
            .map { it -> it.map { it.toDomainModel() } } // Fetches LiveData from the DAO
    }

    suspend fun getPurchasesByDateRange(startDate: String, endDate: String): List<Purchase> {
        return purchaseDao.getPurchasesByDate(startDate, endDate).map { it.toDomainModel() }
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
            } else {
                println("api failed")
                return null
            }
        }
        println("fetched from room")
        return purchase.toDomainModel()
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

