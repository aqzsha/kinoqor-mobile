package com.example.kinoqor.data.remote.dto

data class ResetPasswordRequest(
    val email: String,
    val token: String,
    val new_password: String,
    val new_password_confirmation: String
)