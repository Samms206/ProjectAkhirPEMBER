package com.example.projectakhirecomerce.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectakhirecomerce.model.CartEntity

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCart(cartEntity: CartEntity)

    @Query("SELECT * FROM cartentity ORDER BY id ASC")
    fun getAllCart() : LiveData<List<CartEntity>>

    @Delete
    fun deleteCart(cartEntity: CartEntity)
}