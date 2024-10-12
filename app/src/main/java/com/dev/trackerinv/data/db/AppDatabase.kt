package com.dev.trackerinv.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dev.trackerinv.data.db.dao.PurchaseDao
import com.dev.trackerinv.data.db.dao.SaleDao
import com.dev.trackerinv.data.db.entity.PurchaseEntity
import com.dev.trackerinv.data.db.entity.SaleEntity

@Database(entities = [SaleEntity::class, PurchaseEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class) // To handle complex types like GST
abstract class AppDatabase : RoomDatabase() {
    abstract fun saleDao(): SaleDao
    abstract fun purchaseDao(): PurchaseDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tracker_inventory_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
