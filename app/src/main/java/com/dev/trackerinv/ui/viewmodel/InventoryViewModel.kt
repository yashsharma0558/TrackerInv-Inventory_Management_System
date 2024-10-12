package com.dev.trackerinv.ui.viewmodel


import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.data.repository.InventoryRepository
import kotlinx.coroutines.launch

class InventoryViewModel(private val repository: InventoryRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                repository.syncAllPurchases() // Sync purchases data from API
                repository.syncAllSales() // Sync sales data from API
            } catch (e: Exception) {
                // Handle any errors (e.g., network issues)
            }
        }
    }

    val purchases: LiveData<List<Purchase>> = repository.getAllPurchasesFromRoom()
    val sales: LiveData<List<Sale>> = repository.getAllSalesFromRoom()

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private val _saleResponse = MutableLiveData<Sale?>()
    val saleResponse: MutableLiveData<Sale?> get() = _saleResponse

    private val _purchaseResponse = MutableLiveData<Purchase?>()
    val purchaseResponse: MutableLiveData<Purchase?> get() = _purchaseResponse

    fun createSale(sale: Sale) = viewModelScope.launch {
        repository.addSale(sale) { message ->
            _toastMessage.value = message
        }
    }

    fun createPurchase(purchase: Purchase) = viewModelScope.launch {
        repository.addPurchase(purchase) { message ->
            _toastMessage.value = message
        }
    }

    fun getSaleById(id: String): LiveData<Sale?> {
        viewModelScope.launch {
            val sale = repository.getSaleById(id)
            if (sale != null) {
                _saleResponse.postValue(sale)
            }
        }
        return _saleResponse
    }

    fun getPurchaseById(id: String): LiveData<Purchase?> {
        viewModelScope.launch {
            val purchase = repository.getPurchaseById(id)
            if (purchase != null) {
                _purchaseResponse.postValue(purchase)
            }
        }
        return _purchaseResponse
    }



}
