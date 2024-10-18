package com.dev.trackerinv.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.trackerinv.data.model.Platform
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.data.repository.SaleRepository
import kotlinx.coroutines.launch

class SaleViewModel(private val repository: SaleRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                repository.syncAllSales() // Sync sales data from API
            } catch (e: Exception) {
                // Handle any errors (e.g., network issues)
            }
        }
    }

    val sales: LiveData<List<Sale>> = repository.getAllSalesFromRoom()

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private val _saleResponse = MutableLiveData<Sale?>()
    val saleResponse: MutableLiveData<Sale?> get() = _saleResponse

    val platformOptions: List<Platform> = Platform.entries


    fun createSale(sale: Sale) = viewModelScope.launch {
        repository.addSale(sale) { message ->
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

}
