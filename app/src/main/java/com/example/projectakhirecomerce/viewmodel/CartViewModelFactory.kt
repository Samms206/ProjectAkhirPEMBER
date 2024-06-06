package com.example.projectakhirecomerce.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectakhirecomerce.repository.CartRepository
import com.example.projectakhirecomerce.utils.DependencyInjection

class CartViewModelFactory(private val repository: CartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
    companion object {
        @Volatile
        private var instance: CartViewModelFactory? = null
        fun getInstance(context: Context): CartViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: CartViewModelFactory(DependencyInjection.provideCartRepository(context))
            }.also { instance = it }
    }
}