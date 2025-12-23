package com.example.kinoqor.data.mapper

import com.example.kinoqor.data.local.entity.CinemaEntity
import com.example.kinoqor.data.remote.dto.CinemaDto

fun CinemaDto.toEntity(): CinemaEntity {
    return CinemaEntity(
        id = id,
        name = name,
        address = details?.address,
        description = details?.description,
        latitude = details?.latitude,
        longitude = details?.longitude
    )
}
