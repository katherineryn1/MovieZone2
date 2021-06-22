package com.katherineryn.moviezone.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val NEWEST = "Newest"
    const val OLDEST = "Oldest"
    const val RANDOM = "Random"

    fun getSortedQueryMovies(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM filmentities WHERE isTvShow = 0 ")
        when (filter) {
            NEWEST -> simpleQuery.append("ORDER BY id DESC")
            OLDEST -> simpleQuery.append("ORDER BY id ASC")
            RANDOM -> simpleQuery.append("ORDER BY RANDOM()")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedQueryTvShows(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM filmentities WHERE isTvShow = 1 ")
        when (filter) {
            NEWEST -> simpleQuery.append("ORDER BY id DESC")
            OLDEST -> simpleQuery.append("ORDER BY id ASC")
            RANDOM -> simpleQuery.append("ORDER BY RANDOM()")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}