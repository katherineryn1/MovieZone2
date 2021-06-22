package com.katherineryn.moviezone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowListResponse(
    @field:SerializedName("results")
    val results: List<TvShowResponse>
)

data class TvShowResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("first_air_date")
    val releaseDate: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("poster_path")
    val poster: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("vote_count")
    val voteCount: Int
)