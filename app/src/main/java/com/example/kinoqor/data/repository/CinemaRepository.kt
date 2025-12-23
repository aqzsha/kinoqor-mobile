package com.example.kinoqor.data.repository

import com.example.kinoqor.data.local.dao.CinemaDao
import com.example.kinoqor.data.local.entity.CinemaEntity
import com.example.kinoqor.data.mapper.toEntity
import com.example.kinoqor.data.remote.RetrofitClient

class CinemaRepository(
    private val dao: CinemaDao
) {

    suspend fun fetchCinemas(): Result<List<CinemaEntity>> {
        return try {
            val response = RetrofitClient.api.getCinemas()

            if (response.isSuccessful && response.body()?.success == true) {
                val entities = response.body()!!.data.map { it.toEntity() }
                dao.clear()
                dao.insertAll(entities)
                Result.success(entities)
            } else {
                Result.failure(Exception("Cinema list error"))
            }

        } catch (e: Exception) {
            dao.getAll().takeIf { it.isNotEmpty() }
                ?.let { Result.success(it) }
                ?: Result.failure(e)
        }
    }

    suspend fun getCinemaDetails(id: Long): Result<CinemaEntity> {
        return try {
            val response = RetrofitClient.api.getCinemaById(id)

            if (response.isSuccessful && response.body()?.success == true) {
                val entity = response.body()!!.data.toEntity()
                dao.insert(entity)
                Result.success(entity)
            } else {
                Result.failure(Exception("Cinema details error"))
            }

        } catch (e: Exception) {
            dao.getById(id)?.let { Result.success(it) }
                ?: Result.failure(e)
        }
    }
}
