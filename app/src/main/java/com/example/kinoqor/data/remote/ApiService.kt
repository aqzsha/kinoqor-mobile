package com.example.kinoqor.data.remote


import com.example.kinoqor.data.remote.dto.LoginRequest
import com.example.kinoqor.data.remote.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<LoginResponse>
}