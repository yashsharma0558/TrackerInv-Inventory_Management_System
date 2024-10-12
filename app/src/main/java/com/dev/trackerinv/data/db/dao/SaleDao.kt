package com.dev.trackerinv.data.db.dao

import androidx.room.*
import com.dev.trackerinv.data.db.entity.SaleEntity

@Dao
interface SaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSale(sale: SaleEntity)

    @Query("SELECT * FROM sale WHERE id = :id")
    suspend fun getSaleById(id: String): SaleEntity?

    @Delete
    suspend fun deleteSale(sale: SaleEntity)
}
