package com.example.projectakhirecomerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projectakhirecomerce.model.CartEntity
import com.example.projectakhirecomerce.repository.CartRepository

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    fun insertCart(cart: CartEntity) {
        cartRepository.insertCart(cart)
    }

    fun getAllCart(): LiveData<List<CartEntity>> {
        return cartRepository.getAllCart()
    }

    fun deleteCart(cart: CartEntity) {
        cartRepository.deleteCart(cart)
    }

}