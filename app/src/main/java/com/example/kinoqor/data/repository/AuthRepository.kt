package com.example.kinoqor.data.repository

import com.example.kinoqor.data.remote.RetrofitClient
import com.example.kinoqor.data.remote.dto.*

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

    suspend fun register(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = RetrofitClient.api.register(RegisterRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun forgotPassword(email: String): Result<Unit> {
        return try {
            val response = RetrofitClient.api.forgotPassword(ForgotRequest(email))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send reset email"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyPin(email: String, pinCode: String): Result<String> {
        return try {
            val response = RetrofitClient.api.verifyPin(VerifyPinRequest(email, pinCode))
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.token
                Result.success(token)
            } else {
                Result.failure(Exception("Invalid code or server error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(
        email: String,
        token: String,
        newPassword: String,
        confirmPassword: String
    ): Result<Unit> {
        return try {
            val response = RetrofitClient.api.resetPassword(
                ResetPasswordRequest(
                    email = email,
                    token = token,
                    new_password = newPassword,
                    new_password_confirmation = confirmPassword
                )
            )

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to reset password"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}