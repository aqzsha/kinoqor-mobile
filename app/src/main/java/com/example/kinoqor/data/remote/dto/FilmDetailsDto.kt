    package com.example.kinoqor.data.remote.dto

    import com.google.gson.annotations.SerializedName

    data class FilmDetailsDto(
        @SerializedName("id") val id: Long,
        @SerializedName("duration") val duration: String?,
        @SerializedName("premier") val premier: String?,
        @SerializedName("production") val production: String?,
        @SerializedName("direcor") val director: String?,
        @SerializedName("rate") val rate: Double?,
        @SerializedName("age_limit") val ageLimit: Int?
    )
