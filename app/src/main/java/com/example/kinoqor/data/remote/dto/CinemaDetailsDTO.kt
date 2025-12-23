package com.example.kinoqor.data.remote.dto

data class CinemaDetailsDto(
    val id: Long,
    val name: String,
    val address: String?,
    val description: String?,
    val latitude: Double?,
    val longitude: Double?,
    val created_at: String?
)
