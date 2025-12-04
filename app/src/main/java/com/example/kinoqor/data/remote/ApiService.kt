package com.example.kinoqor.data.remote

import com.example.kinoqor.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<LoginResponse>

    @POST("user/create")
    suspend fun register(
        @Body body: RegisterRequest
    ): Response<LoginResponse>

    @POST("user/password/forgot")
    suspend fun forgotPassword(
        @Body body: ForgotRequest
    ): Response<Void>

    @POST("user/password/verify-pin")
    suspend fun verifyPin(
        @Body body: VerifyPinRequest
    ): Response<VerifyPinResponse>

    @POST("user/password/reset")
    suspend fun resetPassword(
        @Body body: ResetPasswordRequest
    ): Response<Void>

}