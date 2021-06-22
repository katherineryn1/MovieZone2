package com.katherineryn.moviezone.core.data.source.local

import com.katherineryn.moviezone.core.data.source.local.entity.FilmEntity
import com.katherineryn.moviezone.core.data.source.local.room.FilmDao
import com.katherineryn.moviezone.core.utils.SortUtils
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mFilmDao: FilmDao) {
    // movie
    fun getAllMovies(sort: String): Flow<List<FilmEntity>> =
        mFilmDao.getMovies(SortUtils.getSortedQueryMovies(sort))

    fun getFavMovies(): Flow<List<FilmEntity>> =
        mFilmDao.getFavMovies()

    // tv show
    fun getAllTvShows(sort: String): Flow<List<FilmEntity>> =
        mFilmDao.getTvShows(SortUtils.getSortedQueryTvShows(sort))

    fun getFavTvShows(): Flow<List<FilmEntity>> =
        mFilmDao.getFavTvShows()

    // all
    suspend fun insertFilms(films: List<FilmEntity>) = mFilmDao.insertFilms(films)

    fun setFavFilm(film: FilmEntity, newState: Boolean) {
        film.isFav = newState
        mFilmDao.updateFavFilm(film)
    }
}