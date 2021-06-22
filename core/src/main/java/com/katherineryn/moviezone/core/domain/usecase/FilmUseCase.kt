package com.katherineryn.moviezone.core.domain.usecase

import com.katherineryn.moviezone.core.data.Resource
import com.katherineryn.moviezone.core.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmUseCase {
    // movie
    fun getAllMovies(sort: String): Flow<Resource<List<Film>>>

    fun getFavMovies(): Flow<List<Film>>

    // tv show
    fun getAllTvShows(sort: String): Flow<Resource<List<Film>>>

    fun getFavTvShows(): Flow<List<Film>>

    fun setFavFilm(film: Film, newState: Boolean)
}