package com.katherineryn.moviezone.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.katherineryn.moviezone.core.domain.model.Film
import com.katherineryn.moviezone.core.domain.usecase.FilmUseCase

class FavoriteViewModel(private val filmUseCase: FilmUseCase) : ViewModel() {
    fun getFavMovies(): LiveData<List<Film>> =
        filmUseCase.getFavMovies().asLiveData()

    fun getFavTvShows(): LiveData<List<Film>> =
        filmUseCase.getFavTvShows().asLiveData()

    fun setFavFilm(film: Film, newState: Boolean) {
        filmUseCase.setFavFilm(film, newState)
    }
}