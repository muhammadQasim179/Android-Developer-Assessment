package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieModel(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val director: String,
    val producer: String,
    val release_date: String,
    val running_time: String,
    val image: String,
    val movie_banner: String,
    val isFavorite: Boolean = false
)
