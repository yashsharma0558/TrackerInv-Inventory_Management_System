package com.dev.trackerinv.ui.screens

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.R
import com.dev.trackerinv.data.api.ApiService
import com.dev.trackerinv.data.db.AppDatabase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.data.model.GST
import com.dev.trackerinv.data.repository.InventoryRepository
import com.dev.trackerinv.ui.viewmodel.InventoryViewModel
import com.dev.trackerinv.ui.viewmodel.InventoryViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var database: AppDatabase
    private lateinit var textView : TextView
    private lateinit var addButton : Button
    private lateinit var getButton : Button

    private val viewModel: InventoryViewModel by viewModels {
        InventoryViewModelFactory(InventoryRepository(apiService,database))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = application as InventoryApp
        apiService = app.apiService
        database = app.database

        textView = findViewById(R.id.textView)
        addButton = findViewById(R.id.button)
        getButton = findViewById(R.id.button2)

        addButton.setOnClickListener {
            // Example usage: Creating a sale
            val sale = Sale(
                id = "1",
                image = "image_url",
                platform = "Platform",
                date = "2023-10-10",
                stock_id = "S123",
                product_name = "Product",
                cus_name = "Customer",
                cus_ph = "1234567890",
                quantity = 5,
                sp = 100.0,
                gst = GST(5.0, 0.0, 5.0),
                delivery_channel = "Courier",
                delivery_and_other_expenses = 20.0
            )
            println("MAINACTIVITY $sale")
            viewModel.createSale(sale)

            viewModel.toastMessage.observe(this) { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }

        }
        getButton.setOnClickListener {
            viewModel.getSaleById("1").observe(this){
                sale->
                Toast.makeText(this, "Fetched Sale: ${sale?.product_name}", Toast.LENGTH_SHORT).show()
                textView.text = sale?.id + sale?.product_name + sale?.cus_name
            }
        }


    }
}
