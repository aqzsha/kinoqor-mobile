package com.example.kinoqor.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class FilmDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("details_id")
    val detailsId: Long? = null,

    @SerializedName("start_date")
    val startDate: String? = null,

    @SerializedName("end_date")
    val endDate: String? = null,

    @SerializedName("details")
    val details: FilmDetailsDto? = null,

    @SerializedName("poster_url")
    val posterUrl: String? = null,
)
