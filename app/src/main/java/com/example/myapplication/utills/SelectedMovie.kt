package com.example.myapplication.utills

import com.example.myapplication.data.model.MovieModel

object SelectedMovie {
    private var movie: MovieModel? = null

    fun set(movieModel: MovieModel) {
        movie = movieModel
    }
    fun get(): MovieModel? = movie
}