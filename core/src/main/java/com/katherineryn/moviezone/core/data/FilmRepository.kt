package com.katherineryn.moviezone.core.data

import com.katherineryn.moviezone.core.data.source.local.LocalDataSource
import com.katherineryn.moviezone.core.data.source.remote.RemoteDataSource
import com.katherineryn.moviezone.core.data.source.remote.network.ApiResponse
import com.katherineryn.moviezone.core.data.source.remote.response.MovieResponse
import com.katherineryn.moviezone.core.data.source.remote.response.TvShowResponse
import com.katherineryn.moviezone.core.domain.model.Film
import com.katherineryn.moviezone.core.domain.repository.IFilmRepository
import com.katherineryn.moviezone.core.utils.AppExecutors
import com.katherineryn.moviezone.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilmRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IFilmRepository {
    // movie
    override fun getAllMovies(sort: String): Flow<Resource<List<Film>>> =
        object : NetworkBoundResource<List<Film>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Film>> {
                return localDataSource.getAllMovies(sort).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Film>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertFilms(movieList)
            }

        }.asFlow()

    override fun getFavMovies(): Flow<List<Film>> {
        return localDataSource.getFavMovies().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    // tv show
    override fun getAllTvShows(sort: String): Flow<Resource<List<Film>>> =
        object : NetworkBoundResource<List<Film>, List<TvShowResponse>>() {
            override fun loadFromDB(): Flow<List<Film>> {
                return localDataSource.getAllTvShows(sort).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Film>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getTvShows()

            override suspend fun saveCallResult(data: List<TvShowResponse>) {
                val tvShowList = DataMapper.mapTvShowResponsesToEntities(data)
                localDataSource.insertFilms(tvShowList)
            }

        }.asFlow()

    override fun getFavTvShows(): Flow<List<Film>> {
        return localDataSource.getFavTvShows().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    // all
    override fun setFavFilm(film: Film, newState: Boolean) {
        val filmEntity = DataMapper.mapDomainToEntity(film)
        appExecutors.diskIO().execute { localDataSource.setFavFilm(filmEntity, newState) }
    }

}