package com.example.projectakhirecomerce.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectakhirecomerce.api.ApiResponse
import com.example.projectakhirecomerce.api.ApiService
import com.example.projectakhirecomerce.model.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

// ProductRepository.kt
class ProductRepository {

    fun getProducts(): LiveData<ApiResponse<List<Product>>> {
        val productsLiveData = MutableLiveData<ApiResponse<List<Product>>>()

        ApiService.apiRequest.getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    productsLiveData.postValue(ApiResponse.Success(response.body() ?: emptyList()))
                } else {
                    productsLiveData.postValue(ApiResponse.Error("Failed to get products"))
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                productsLiveData.postValue(ApiResponse.Error("Failed to connect to server"))
            }
        })

        return productsLiveData
    }
}