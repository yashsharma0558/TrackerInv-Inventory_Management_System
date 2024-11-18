package com.dev.trackerinv.ui.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.data.repository.PurchaseRepository
import com.dev.trackerinv.domain.usecase.FilterPurchasesByDateUseCase
import com.dev.trackerinv.domain.usecase.FilterSalesByDateUseCase
import kotlinx.coroutines.launch

class PurchaseViewModel(private val repository: PurchaseRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                repository.syncAllPurchases() // Sync purchases data from API
            } catch (e: Exception) {
                // Handle any errors (e.g., network issues)
            }
        }
    }

    val purchases: LiveData<List<Purchase>> = repository.getAllPurchasesFromRoom()

    val filteredPurchases = MutableLiveData<List<Purchase>>()

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private val _purchaseResponse = MutableLiveData<Purchase?>()
    val purchaseResponse: MutableLiveData<Purchase?> get() = _purchaseResponse


    fun createPurchase(purchase: Purchase) = viewModelScope.launch {
        repository.addPurchase(purchase) { message ->
            _toastMessage.value = message
        }
    }

    fun filterPurchasesByDate(startDate: String, endDate: String) {
        if (startDate.isEmpty() || endDate.isEmpty()) {
            filteredPurchases.postValue(purchases.value)
            return
        }
        viewModelScope.launch {
            val filteredPurchase = FilterPurchasesByDateUseCase(repository).invoke(startDate, endDate)
            filteredPurchases.postValue(filteredPurchase)
        }
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
