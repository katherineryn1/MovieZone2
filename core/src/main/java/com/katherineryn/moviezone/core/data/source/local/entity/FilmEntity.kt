package com.katherineryn.moviezone.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filmentities")
data class FilmEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "popularity")
    var popularity: Double,

    @ColumnInfo(name = "poster")
    var poster: String,

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double,

    @ColumnInfo(name = "voteCount")
    var voteCount: Int,

    @ColumnInfo(name = "isFav")
    var isFav: Boolean = false,

    @ColumnInfo(name = "isTvShow")
    var isTvShow: Boolean = false
)
