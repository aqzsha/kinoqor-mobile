package com.example.kinoqor.ui.cinema

import androidx.lifecycle.*
import com.example.kinoqor.data.local.entity.CinemaEntity
import com.example.kinoqor.data.repository.CinemaRepository
import kotlinx.coroutines.launch

class CinemaDetailsViewModel(
    private val repo: CinemaRepository
) : ViewModel() {

    private val _cinema = MutableLiveData<CinemaEntity>()
    val cinema: LiveData<CinemaEntity> = _cinema

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun load(id: Long) {
        viewModelScope.launch {
            repo.getCinemaDetails(id)
                .onSuccess { _cinema.value = it }
                .onFailure { _error.value = it.message }
        }
    }
}
