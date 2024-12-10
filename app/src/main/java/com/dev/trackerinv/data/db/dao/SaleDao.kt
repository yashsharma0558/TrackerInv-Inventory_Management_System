package com.dev.trackerinv.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dev.trackerinv.data.db.entity.SaleEntity
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.domain.model.RevenueData
import com.dev.trackerinv.domain.model.RevenueSaleData

@Dao
interface SaleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSales(sales: List<SaleEntity>)

    @Query("SELECT * FROM sale")
    fun getAllSales(): LiveData<List<SaleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSale(sale: SaleEntity)

    @Query("SELECT * FROM sale WHERE id = :id")
    suspend fun getSaleById(id: String): SaleEntity?

    @Delete
    suspend fun deleteSale(sale: SaleEntity)

    @Query("SELECT * FROM sale WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getSalesByDateRange(startDate: String, endDate: String): List<SaleEntity>

    @Query(
        """
        SELECT SUM(sp*quantity) 
        FROM sale 
        WHERE strftime('%Y-%m-%d', substr(date, 7, 4) || '-' || substr(date, 4, 2) || '-' || substr(date, 1, 2)) 
        BETWEEN strftime('%Y-%m-%d', :startDate) AND strftime('%Y-%m-%d', :endDate)
    """
    )
    suspend fun getTotalSales(startDate: String, endDate: String): Double

    @Query("SELECT SUM(sp*quantity) FROM sale")
    suspend fun getTotalSales(): Double

}
