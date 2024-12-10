package com.dev.trackerinv.data.db.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.dev.trackerinv.data.db.entity.PurchaseEntity
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.domain.model.RevenueData
import com.dev.trackerinv.domain.model.RevenuePurchaseData

@Dao
interface PurchaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPurchases(purchases: List<PurchaseEntity>)

    @Query("SELECT * FROM purchase")
    fun getAllPurchases(): LiveData<List<PurchaseEntity>> // LiveData to observe changes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchase(purchase: PurchaseEntity)

    @Query("SELECT * FROM purchase WHERE id = :id")
    suspend fun getPurchaseById(id: String): PurchaseEntity?

    @Delete
    suspend fun deletePurchase(purchase: PurchaseEntity)

    @Query("SELECT * FROM purchase WHERE invDate BETWEEN :startDate AND :endDate")
    suspend fun getPurchasesByDate(startDate: String, endDate: String): List<PurchaseEntity>

    @Query(
        """
        SELECT SUM(pricePerUnit*quantity)
        FROM purchase 
        WHERE strftime('%Y-%m-%d', substr(invDate, 7, 4) || '-' || substr(invDate, 4, 2) || '-' || substr(invDate, 1, 2)) 
        BETWEEN strftime('%Y-%m-%d', :startDate) AND strftime('%Y-%m-%d', :endDate)
    """
    )
    suspend fun getTotalPurchases(startDate: String, endDate: String): Double

    @Query("SELECT SUM(pricePerUnit*quantity) FROM purchase")
    suspend fun getTotalPurchases(): Double


}
