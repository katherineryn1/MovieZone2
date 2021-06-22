package com.katherineryn.moviezone.core.domain.usecase

import com.katherineryn.moviezone.core.data.Resource
import com.katherineryn.moviezone.core.domain.model.Film
import com.katherineryn.moviezone.core.domain.repository.IFilmRepository
import kotlinx.coroutines.flow.Flow

class FilmInteractor(private val filmRepository: IFilmRepository) : FilmUseCase {
    override fun getAllMovies(sort: String): Flow<Resource<List<Film>>> =
        filmRepository.getAllMovies(sort)

    override fun getFavMovies(): Flow<List<Film>> =
        filmRepository.getFavMovies()

    override fun getAllTvShows(sort: String): Flow<Resource<List<Film>>> =
        filmRepository.getAllTvShows(sort)

    override fun getFavTvShows(): Flow<List<Film>> =
        filmRepository.getFavTvShows()

    override fun setFavFilm(film: Film, newState: Boolean) =
        filmRepository.setFavFilm(film, newState)
}