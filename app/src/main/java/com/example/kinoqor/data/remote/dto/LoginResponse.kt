package com.example.kinoqor.data.remote.dto

data class LoginResponse(
    val data: LoginData,
    val message: String,
    val success: Boolean
)