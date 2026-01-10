package com.example.myapplication.data.repository
import com.example.myapplication.data.local.MovieDao
import com.example.myapplication.data.model.MovieModel
import kotlinx.coroutines.flow.Flow

class FavouriteRepository(private val movieDao: MovieDao) {

    fun getAllMovies(): Flow<List<MovieModel>> = movieDao.getAllMovies()
    fun getFavoriteMovies(): Flow<List<MovieModel>> = movieDao.getFavoriteMovies()

    suspend fun insertMovie(movie: MovieModel) = movieDao.insertMovie(movie)
    suspend fun updateMovie(movie: MovieModel) = movieDao.updateMovie(movie)
    suspend fun deleteMovie(movie: MovieModel) = movieDao.deleteMovie(movie)

    suspend fun toggleFavorite(movie: MovieModel) {
        val updatedMovie = movie.copy(isFavorite = !movie.isFavorite)
        movieDao.insertMovie(updatedMovie)
    }
}