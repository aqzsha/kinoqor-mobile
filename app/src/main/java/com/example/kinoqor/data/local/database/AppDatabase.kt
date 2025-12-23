package com.example.kinoqor.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kinoqor.data.local.dao.FilmDao
import com.example.kinoqor.data.local.entity.FilmEntity
import com.example.kinoqor.data.local.dao.CinemaDao
import com.example.kinoqor.data.local.entity.CinemaEntity

@Database(
    entities = [
        FilmEntity::class,
        CinemaEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao

    abstract fun cinemaDao(): CinemaDao
}


