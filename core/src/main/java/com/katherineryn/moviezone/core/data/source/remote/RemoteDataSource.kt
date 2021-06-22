package com.katherineryn.moviezone.core.data.source.remote

import android.util.Log
import com.katherineryn.moviezone.core.data.source.remote.network.ApiResponse
import com.katherineryn.moviezone.core.data.source.remote.network.ApiService
import com.katherineryn.moviezone.core.data.source.remote.network.NetworkConst.API_KEY
import com.katherineryn.moviezone.core.data.source.remote.response.MovieResponse
import com.katherineryn.moviezone.core.data.source.remote.response.TvShowResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getMovies(API_KEY)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSourceMovie", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShows(): Flow<ApiResponse<List<TvShowResponse>>> {
        return flow {
            try {
                val response = apiService.getTvShows(API_KEY)
                val tvShowList = response.results
                if (tvShowList.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSourceTv", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}