package com.example.myapplication.data.remote
import com.example.myapplication.data.model.MovieModel
import retrofit2.http.GET

interface MovieApi {
    @GET("films")
    suspend fun getMovies(): List<MovieModel>
}
