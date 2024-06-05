package com.example.projectakhirecomerce.repository

import androidx.lifecycle.LiveData
import com.example.projectakhirecomerce.model.UserDatabase
import com.example.projectakhirecomerce.room.UserDao
import com.example.projectakhirecomerce.utils.AppExecutors

class UserRepository private constructor(private val userDao: UserDao, private val appExecutors: AppExecutors) {

    fun getAllPost(): LiveData<List<UserDatabase>> = userDao.getAllUser()

    fun insertPost(user: UserDatabase) {
        appExecutors.diskIO().execute { userDao.insertUser(user) }
    }

    fun login(email: String, password: String, callback: (UserDatabase?) -> Unit) {
        appExecutors.diskIO().execute {
            val user = userDao.getUserByEmail(email)
            appExecutors.mainThread().execute {
                if (user != null && user.password == password) {
                    callback(user)
                } else {
                    callback(null)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userDao: UserDao,
            appExecutors: AppExecutors
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(userDao, appExecutors)
        }.also { instance = it }
    }
}
