package com.example.projectakhirecomerce.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectakhirecomerce.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM userentity ORDER BY id ASC")
    fun getAllUser() : LiveData<List<UserEntity>>

    @Query("SELECT * FROM userentity WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): UserEntity?
}
