package com.dev.trackerinv.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InventoryViewModelFactory<T : ViewModel, R>(
    private val viewModelClass: Class<T>,
    private val repository: R
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the ViewModel class matches the one we expect
        if (modelClass.isAssignableFrom(viewModelClass)) {
            @Suppress("UNCHECKED_CAST")
            return viewModelClass.getConstructor(repository!!::class.java)
                .newInstance(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

