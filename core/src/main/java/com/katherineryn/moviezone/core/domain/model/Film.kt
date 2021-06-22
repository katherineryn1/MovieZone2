package com.katherineryn.moviezone.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val id: Int,
    var overview: String,
    var popularity: Double,
    var poster: String,
    var releaseDate: String,
    var title: String,
    var voteAverage: Double,
    var voteCount: Int,
    var isFav: Boolean = false,
    var isTvShow: Boolean = false
) : Parcelable