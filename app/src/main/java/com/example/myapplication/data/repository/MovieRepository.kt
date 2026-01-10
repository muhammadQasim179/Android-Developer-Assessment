package com.example.myapplication.data.repository

import com.example.myapplication.data.model.MovieModel
import com.example.myapplication.data.remote.MovieApi

class MovieRepository(
    private val api: MovieApi
) {
    suspend fun getMovies(): List<MovieModel> {
        return api.getMovies()
    }

}
