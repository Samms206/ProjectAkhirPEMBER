package com.example.projectakhirecomerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projectakhirecomerce.model.UserDatabase
import com.example.projectakhirecomerce.repository.UserRepository

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun insertUser(user: UserDatabase) {
        userRepository.insertPost(user)
    }

    fun getAllUser(): LiveData<List<UserDatabase>> {
        return userRepository.getAllPost()
    }

    fun login(email: String, password: String, callback: (UserDatabase?) -> Unit) {
        userRepository.login(email, password, callback)
    }
}
