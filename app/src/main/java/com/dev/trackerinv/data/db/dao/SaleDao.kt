package com.dev.trackerinv.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dev.trackerinv.data.db.entity.SaleEntity
import com.dev.trackerinv.data.model.Sale

@Dao
interface SaleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSales(sales: List<SaleEntity>)

    @Query("SELECT * FROM sale")
    fun getAllSales(): LiveData<List<SaleEntity>> // LiveData to observe changes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSale(sale: SaleEntity)

    @Query("SELECT * FROM sale WHERE id = :id")
    suspend fun getSaleById(id: String): SaleEntity?

    @Delete
    suspend fun deleteSale(sale: SaleEntity)
}
