package com.katherineryn.moviezone.core.data.source.remote.network

import com.katherineryn.moviezone.core.data.source.remote.response.MovieListResponse
import com.katherineryn.moviezone.core.data.source.remote.response.TvShowListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): MovieListResponse

    @GET("tv/on_the_air")
    suspend fun getTvShows(
        @Query("api_key") apiKey: String
    ): TvShowListResponse
}