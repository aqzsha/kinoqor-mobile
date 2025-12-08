package com.example.kinoqor.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FilmListResponse(
    @SerializedName("data") val data: List<FilmDto>?,
    @SerializedName("films") val films: List<FilmDto>?
)
