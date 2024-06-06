package com.example.projectakhirecomerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projectakhirecomerce.model.UserEntity
import com.example.projectakhirecomerce.repository.UserRepository

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun insertUser(user: UserEntity) {
        userRepository.insertUser(user)
    }

    fun getAllUser(): LiveData<List<UserEntity>> {
        return userRepository.getAllUser()
    }

    fun login(email: String, password: String, callback: (UserEntity?) -> Unit) {
        userRepository.login(email, password, callback)
    }
}
