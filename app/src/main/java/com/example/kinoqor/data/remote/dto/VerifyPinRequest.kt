package com.example.kinoqor.data.remote.dto

data class VerifyPinRequest(
    val email: String,
    val pin_code: String
)
