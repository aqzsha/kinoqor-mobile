package com.example.kinoqor.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinoqor.data.local.entity.FilmEntity

@Dao
interface FilmDao {

    @Query("SELECT * FROM films")
    suspend fun getAll(): List<FilmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(films: List<FilmEntity>)

    @Query("DELETE FROM films")
    suspend fun clear()


    @Query("SELECT * FROM films WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): FilmEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(film: FilmEntity)
}
