package com.example.kinoqor.data.mapper

import com.example.kinoqor.data.local.entity.FilmEntity
import com.example.kinoqor.data.remote.dto.FilmDto

fun FilmDto.toEntity(): FilmEntity {
    return FilmEntity(
        id = id,
        name = name,
        description = description,
        posterUrl = posterUrl,

        startDate = startDate,
        endDate = endDate,

        duration = details?.duration,
        premier = details?.premier,
        production = details?.production,
        director = details?.director,

        rate = details?.rate,
        ageLimit = details?.ageLimit
    )
}

