package com.dev.trackerinv.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.R
import com.dev.trackerinv.data.api.ApiService
import com.dev.trackerinv.data.db.AppDatabase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.data.model.GST
import com.dev.trackerinv.ui.viewmodel.InventoryViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_sale -> {
                    navController.navigate(R.id.saleFragment)
                    true
                }
                R.id.nav_purchase -> {
                    navController.navigate(R.id.purchaseFragment)
                    true
                }
                R.id.nav_report -> {
                    navController.navigate(R.id.reportFragment)
                    true
                }
                else -> false
            }
        }
    }
}
