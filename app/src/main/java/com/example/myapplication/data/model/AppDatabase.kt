package com.example.myapplication.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.MovieDao

@Database(entities = [MovieModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}