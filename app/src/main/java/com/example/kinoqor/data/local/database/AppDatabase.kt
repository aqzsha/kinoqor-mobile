package com.example.kinoqor.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kinoqor.data.local.dao.FilmDao
import com.example.kinoqor.data.local.entity.FilmEntity

@Database(
    entities = [FilmEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}

