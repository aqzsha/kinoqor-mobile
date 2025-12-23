package com.example.kinoqor.data.remote

import com.example.kinoqor.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/user/create")
    suspend fun register(
        @Body body: RegisterRequest
    ): Response<LoginResponse>

    @POST("auth/password/forgot")
    suspend fun forgotPassword(
        @Body body: ForgotRequest
    ): Response<Void>

    @POST("auth/password/verify-pin")
    suspend fun verifyPin(
        @Body body: VerifyPinRequest
    ): Response<VerifyPinResponse>

    @POST("auth/password/reset")
    suspend fun resetPassword(
        @Body body: ResetPasswordRequest
    ): Response<Void>

    @GET("film/list")
    suspend fun getFilmList(): Response<ApiResponse<List<FilmDto>>>


    @GET("film/get/{id}")
    suspend fun getFilmById(
        @Path("id") id: Long
    ): Response<ApiResponse<FilmDto>>

    @GET("cinema/list")
    suspend fun getCinemas(): Response<ApiResponse<List<CinemaDto>>>

    @GET("cinema/get/{id}")
    suspend fun getCinemaById(
        @Path("id") id: Long
    ): Response<ApiResponse<CinemaDto>>
}