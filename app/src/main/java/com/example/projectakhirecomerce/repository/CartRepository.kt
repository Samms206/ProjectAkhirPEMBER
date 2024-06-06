package com.example.projectakhirecomerce.repository

import androidx.lifecycle.LiveData
import com.example.projectakhirecomerce.model.CartEntity
import com.example.projectakhirecomerce.model.UserEntity
import com.example.projectakhirecomerce.room.CartDao
import com.example.projectakhirecomerce.room.UserDao
import com.example.projectakhirecomerce.utils.AppExecutors

class CartRepository private constructor(private val cartDao: CartDao, private val appExecutors: AppExecutors) {

    fun getAllCart(): LiveData<List<CartEntity>> = cartDao.getAllCart()

    fun insertCart(cart: CartEntity) {
        appExecutors.diskIO().execute { cartDao.insertCart(cart) }
    }

    fun deleteCart(cart: CartEntity) {
        appExecutors.diskIO().execute { cartDao.deleteCart(cart) }
    }

    companion object {
        @Volatile
        private var instance: CartRepository? = null

        fun getInstance(
            cartDao: CartDao,
            appExecutors: AppExecutors
        ): CartRepository = instance ?: synchronized(this) {
            instance ?: CartRepository(cartDao, appExecutors)
        }.also { instance = it }
    }
}