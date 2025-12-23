package com.example.kinoqor.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoqor.data.repository.FilmRepository

class MovieDetailsViewModelFactory(
    private val repo: FilmRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(c: Class<T>): T {
        return MovieDetailsViewModel(repo) as T
    }
}
