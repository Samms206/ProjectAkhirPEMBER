package com.example.projectakhirecomerce.viewmodel

// ProductViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectakhirecomerce.api.ApiResponse
import com.example.projectakhirecomerce.model.Product
import com.example.projectakhirecomerce.repository.ProductRepository

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val products = MutableLiveData<ApiResponse<List<Product>>>()

    fun getProducts() {
        products.value = ApiResponse.Loading
        repository.getProducts().observeForever { response ->
            products.value = response
        }
    }

    fun getProductsByCategory(category: String) {
        products.value = ApiResponse.Loading
        repository.getProductsByCategory(category).observeForever { response ->
            products.value = response
        }
    }
}


