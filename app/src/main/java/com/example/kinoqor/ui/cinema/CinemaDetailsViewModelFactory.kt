package com.example.kinoqor.ui.cinema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoqor.data.repository.CinemaRepository

class CinemaDetailsViewModelFactory(
    private val repo: CinemaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CinemaDetailsViewModel(repo) as T
    }
}
