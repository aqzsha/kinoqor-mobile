package com.example.kinoqor.data.repository

import com.example.kinoqor.data.remote.RetrofitClient
import com.example.kinoqor.data.remote.dto.FilmDto

class FilmRepository {
    suspend fun fetchFilms(): Result<List<FilmDto>> {
        return try {
            val response = RetrofitClient.api.getFilmList()
            if (response.isSuccessful) {
                val body = response.body() ?: emptyList()
                Result.success(body)
            } else {
                Result.failure(Exception("Server error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
