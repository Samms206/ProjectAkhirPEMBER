package com.example.projectakhirecomerce.api


sealed class ApiResponse<T> {
    class Success<T>(val data: T) : ApiResponse<T>()
    class Error<T>(val message: String) : ApiResponse<T>()
}