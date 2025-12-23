package com.example.kinoqor.ui.cinema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoqor.data.repository.CinemaRepository

class CinemasViewModelFactory(
    private val repo: CinemaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CinemasViewModel(repo) as T
    }
}
