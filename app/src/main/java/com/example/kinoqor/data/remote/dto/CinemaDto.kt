package com.example.kinoqor.data.remote.dto

data class CinemaDto(
    val id: Long,
    val name: String,
    val details_id: Long?,
    val details: CinemaDetailsDto?
)
