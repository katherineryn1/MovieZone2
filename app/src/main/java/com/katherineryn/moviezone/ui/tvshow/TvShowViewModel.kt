package com.katherineryn.moviezone.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.katherineryn.moviezone.core.data.Resource
import com.katherineryn.moviezone.core.domain.model.Film
import com.katherineryn.moviezone.core.domain.usecase.FilmUseCase

class TvShowViewModel(private val filmUseCase: FilmUseCase) : ViewModel() {

    fun getTvShows(sort: String): LiveData<Resource<List<Film>>> {
        return filmUseCase.getAllTvShows(sort).asLiveData()
    }
}