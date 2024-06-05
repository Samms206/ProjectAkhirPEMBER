package com.example.projectakhirecomerce.utils

import android.content.Context
import com.example.projectakhirecomerce.repository.UserRepository
import com.example.projectakhirecomerce.room.AppDatabase

object DependencyInjection {
    fun provideRepository(context: Context): UserRepository {
        val database = AppDatabase.getDatabase(context)
        val appExecutors = AppExecutors()
        val dao = database.userDao()
        return UserRepository.getInstance(dao, appExecutors)
    }
}