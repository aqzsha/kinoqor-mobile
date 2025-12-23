package com.example.kinoqor.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmEntity(

    @PrimaryKey val id: Long,
    val name: String,
    val description: String?,
    val posterUrl: String?,

    val startDate: String?,
    val endDate: String?,

    val duration: String?,
    val premier: String?,
    val production: String?,
    val director: String?,

    val rate: Double?,
    val ageLimit: Int?
)