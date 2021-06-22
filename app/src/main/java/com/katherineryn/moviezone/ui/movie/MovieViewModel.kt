package com.katherineryn.moviezone.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.katherineryn.moviezone.core.data.Resource
import com.katherineryn.moviezone.core.domain.model.Film
import com.katherineryn.moviezone.core.domain.usecase.FilmUseCase

class MovieViewModel(private val filmUseCase: FilmUseCase) : ViewModel() {

    fun getMovies(sort: String): LiveData<Resource<List<Film>>> {
        return filmUseCase.getAllMovies(sort).asLiveData()
    }
}