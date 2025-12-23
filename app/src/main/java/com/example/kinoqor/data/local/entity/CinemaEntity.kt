package com.example.kinoqor.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cinemas")
data class CinemaEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val address: String?,
    val description: String?,
    val latitude: Double?,
    val longitude: Double?
)
