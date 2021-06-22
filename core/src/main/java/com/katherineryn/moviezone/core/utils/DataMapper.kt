package com.katherineryn.moviezone.core.utils

import com.katherineryn.moviezone.core.data.source.local.entity.FilmEntity
import com.katherineryn.moviezone.core.data.source.remote.response.MovieResponse
import com.katherineryn.moviezone.core.data.source.remote.response.TvShowResponse
import com.katherineryn.moviezone.core.domain.model.Film

object DataMapper {
    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<FilmEntity> {
        val movieList = ArrayList<FilmEntity>()
        input.map {
            val movie = FilmEntity(
                id = it.id,
                overview = it.overview,
                popularity = it.popularity,
                poster = it.poster,
                releaseDate = it.releaseDate,
                title = it.title,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                isFav = false,
                isTvShow = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapTvShowResponsesToEntities(input: List<TvShowResponse>): List<FilmEntity> {
        val tvShowList = ArrayList<FilmEntity>()
        input.map {
            val tvShow = FilmEntity(
                id = it.id,
                overview = it.overview,
                popularity = it.popularity,
                poster = it.poster,
                releaseDate = it.releaseDate,
                title = it.name,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                isFav = false,
                isTvShow = true
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapEntitiesToDomain(input: List<FilmEntity>): List<Film> =
        input.map {
            Film(
                id = it.id,
                overview = it.overview,
                popularity = it.popularity,
                poster = it.poster,
                releaseDate = it.releaseDate,
                title = it.title,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                isFav = it.isFav,
                isTvShow = it.isTvShow
            )
        }

    fun mapDomainToEntity(input: Film) = FilmEntity(
        id = input.id,
        overview = input.overview,
        popularity = input.popularity,
        poster = input.poster,
        releaseDate = input.releaseDate,
        title = input.title,
        voteAverage = input.voteAverage,
        voteCount = input.voteCount,
        isFav = input.isFav,
        isTvShow = input.isTvShow
    )
}