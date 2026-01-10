package com.example.myapplication.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.myapplication.data.model.MovieModel
import com.example.myapplication.data.repository.FavouriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository: FavouriteRepository) : ViewModel() {

    private val _favoriteMovies = MutableStateFlow<List<MovieModel>>(emptyList())
    val favoriteMovies: StateFlow<List<MovieModel>> = _favoriteMovies

    init {
        viewModelScope.launch {
            repository.getFavoriteMovies().collect {
                _favoriteMovies.value = it
            }
        }
    }

    fun toggleFavorite(movie: MovieModel) {
        viewModelScope.launch {
            val isFavorite = _favoriteMovies.value.any { it.id == movie.id }

            if (isFavorite) {
                repository.deleteMovie(movie)
            } else {
                repository.insertMovie(movie.copy(isFavorite = true))
            }
        }
    }

    fun isFavorite(movie: MovieModel): Boolean {
        return _favoriteMovies.value.any { it.id == movie.id }
    }
}