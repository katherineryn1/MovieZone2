package com.katherineryn.moviezone.favorite.di

import com.katherineryn.moviezone.favorite.ui.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favModule = module {
    viewModel {
        FavoriteViewModel(get())
    }
}