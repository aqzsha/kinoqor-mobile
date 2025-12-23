package com.example.kinoqor.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FilmDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("details_id") val detailsId: Long?,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("end_date") val endDate: String?,
    @SerializedName("details") val details: FilmDetailsDto?,
    @SerializedName("poster_url") val posterUrl: String?
)
