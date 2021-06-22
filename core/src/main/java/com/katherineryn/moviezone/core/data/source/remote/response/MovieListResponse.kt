package com.katherineryn.moviezone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @field:SerializedName("results")
    val results: List<MovieResponse>
)

data class MovieResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("poster_path")
    val poster: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("vote_count")
    val voteCount: Int,
)
