package com.example.myapplication.data.local


import androidx.room.*
import com.example.myapplication.data.model.MovieModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieModel>>

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieModel)

    @Update
    suspend fun updateMovie(movie: MovieModel)

    @Delete
    suspend fun deleteMovie(movie: MovieModel)
}