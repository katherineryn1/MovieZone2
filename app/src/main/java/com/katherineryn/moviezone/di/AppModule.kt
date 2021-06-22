package com.katherineryn.moviezone.di

import com.katherineryn.moviezone.core.domain.usecase.FilmInteractor
import com.katherineryn.moviezone.core.domain.usecase.FilmUseCase
import com.katherineryn.moviezone.ui.detail.DetailViewModel
import com.katherineryn.moviezone.ui.movie.MovieViewModel
import com.katherineryn.moviezone.ui.tvshow.TvShowViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<FilmUseCase> { FilmInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}