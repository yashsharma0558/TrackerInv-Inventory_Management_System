package com.dev.trackerinv.data.db.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.dev.trackerinv.data.db.entity.PurchaseEntity
import com.dev.trackerinv.data.model.Purchase

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
}
