package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.MovieModel
import com.example.myapplication.data.repository.MovieRepository
import com.example.myapplication.utills.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<UiState<List<MovieModel>>>(UiState.Loading)
    val movies: StateFlow<UiState<List<MovieModel>>> = _movies

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _movies.value = UiState.Loading
            try {
                val response = repository.getMovies()
                _movies.value = UiState.Success(response)
            } catch (e: Exception) {
                _movies.value = UiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
    fun reload(){
        fetchMovies()
    }

}
