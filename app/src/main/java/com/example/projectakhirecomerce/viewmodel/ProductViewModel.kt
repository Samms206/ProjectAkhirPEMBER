package com.example.projectakhirecomerce.viewmodel

// ProductViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projectakhirecomerce.api.ApiResponse
import com.example.projectakhirecomerce.model.Product
import com.example.projectakhirecomerce.repository.ProductRepository

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    fun getProducts(): LiveData<ApiResponse<List<Product>>> {
        return repository.getProducts()
    }
}