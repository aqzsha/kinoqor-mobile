package com.example.kinoqor.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("access_token")
    val token: String,

    @SerializedName("refresh_token")
    val refreshToken: String,

    @SerializedName("token_type")
    val tokenType: String
)
