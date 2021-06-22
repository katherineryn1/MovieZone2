package com.katherineryn.moviezone.ui.detail

import androidx.lifecycle.ViewModel
import com.katherineryn.moviezone.core.domain.model.Film
import com.katherineryn.moviezone.core.domain.usecase.FilmUseCase

class DetailViewModel(private val filmUseCase: FilmUseCase) : ViewModel() {

    fun setFavFilm(film: Film, newState: Boolean) {
        filmUseCase.setFavFilm(film, newState)
    }

    fun parseDate(date: String): String {
        val dateArray = date.split("-")
        val month = dateArray[1].toInt() - 1
        return "${MONTH_ARRAY[month]} ${dateArray[2]}, ${dateArray[0]}"
    }

    companion object {
        private val MONTH_ARRAY = arrayOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
    }
}