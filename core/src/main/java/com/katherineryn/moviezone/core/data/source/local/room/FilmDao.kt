package com.katherineryn.moviezone.core.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.katherineryn.moviezone.core.data.source.local.entity.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    // movie
    @RawQuery(observedEntities = [FilmEntity::class])
    fun getMovies(query: SupportSQLiteQuery): Flow<List<FilmEntity>>

    @Query("SELECT * FROM filmentities WHERE isTvShow = 0 AND isFav = 1")
    fun getFavMovies(): Flow<List<FilmEntity>>

    // tv show
    @RawQuery(observedEntities = [FilmEntity::class])
    fun getTvShows(query: SupportSQLiteQuery): Flow<List<FilmEntity>>

    @Query("SELECT * FROM filmentities WHERE isTvShow = 1 AND isFav = 1")
    fun getFavTvShows(): Flow<List<FilmEntity>>

    // all
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilms(films: List<FilmEntity>)

    @Update
    fun updateFavFilm(film: FilmEntity)
}