package com.example.kinoqor.data.repository

import com.example.kinoqor.data.remote.RetrofitClient
import com.example.kinoqor.data.remote.dto.LoginRequest
import com.example.kinoqor.data.remote.dto.LoginResponse

class AuthRepository {

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = RetrofitClient.api.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Invalid credentials or server error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}