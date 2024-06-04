package com.example.projectakhirecomerce.api

import com.example.projectakhirecomerce.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {

    @GET("dataproduct")
    fun getProducts(): Call<List<Product>>
}