package com.dev.trackerinv.data.db.dao


import androidx.room.*
import com.dev.trackerinv.data.db.entity.PurchaseEntity

@Dao
interface PurchaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchase(purchase: PurchaseEntity)

    @Query("SELECT * FROM purchase WHERE id = :id")
    suspend fun getPurchaseById(id: String): PurchaseEntity?

    @Delete
    suspend fun deletePurchase(purchase: PurchaseEntity)
}
