package com.example.kinoqor.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinoqor.data.local.entity.CinemaEntity

@Dao
interface CinemaDao {

    @Query("SELECT * FROM cinemas")
    suspend fun getAll(): List<CinemaEntity>

    @Query("SELECT * FROM cinemas WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): CinemaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CinemaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CinemaEntity)

    @Query("DELETE FROM cinemas")
    suspend fun clear()
}