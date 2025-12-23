package com.example.kinoqor.data.repository

import com.example.kinoqor.data.local.dao.FilmDao
import com.example.kinoqor.data.local.entity.FilmEntity
import com.example.kinoqor.data.mapper.toEntity
import com.example.kinoqor.data.remote.RetrofitClient

class FilmRepository(
    private val filmDao: FilmDao
) {

    suspend fun fetchFilms(): Result<List<FilmEntity>> {
        return try {
            val response = RetrofitClient.api.getFilmList()

            if (response.isSuccessful && response.body()?.success == true) {

                val entities = response.body()!!.data.map { it.toEntity() }

                filmDao.clear()
                filmDao.insertAll(entities)

                Result.success(entities)

            } else {
                Result.failure(
                    Exception(response.body()?.message ?: "Unknown server error")
                )
            }

        } catch (e: Exception) {

            val cached = filmDao.getAll()
            if (cached.isNotEmpty()) {
                Result.success(cached)
            } else {
                Result.failure(e)
            }
        }
    }

    suspend fun getFilmDetails(id: Long): Result<FilmEntity> {
        return try {
            val response = RetrofitClient.api.getFilmById(id)

            if (response.isSuccessful && response.body()?.success == true) {

                val entity = response.body()!!.data.toEntity()
                filmDao.insert(entity)

                Result.success(entity)

            } else {
                Result.failure(
                    Exception(response.body()?.message ?: "Details error")
                )
            }

        } catch (e: Exception) {

            filmDao.getById(id)?.let {
                Result.success(it)
            } ?: Result.failure(e)
        }
    }
}
