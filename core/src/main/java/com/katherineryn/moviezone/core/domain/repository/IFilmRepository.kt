package com.katherineryn.moviezone.core.domain.repository

import com.katherineryn.moviezone.core.data.Resource
import com.katherineryn.moviezone.core.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface IFilmRepository {
    // movie
    fun getAllMovies(sort: String): Flow<Resource<List<Film>>>

    fun getFavMovies(): Flow<List<Film>>

    // tv show
    fun getAllTvShows(sort: String): Flow<Resource<List<Film>>>

    fun getFavTvShows(): Flow<List<Film>>

    // all
    fun setFavFilm(film: Film, newState: Boolean)
}