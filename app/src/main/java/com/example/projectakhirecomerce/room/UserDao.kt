package com.example.projectakhirecomerce.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectakhirecomerce.model.UserDatabase

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(postDatabase: UserDatabase)

    @Query("SELECT * FROM userdatabase ORDER BY id ASC")
    fun getAllUser() : LiveData<List<UserDatabase>>

    @Query("SELECT * FROM userdatabase WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): UserDatabase?
}
