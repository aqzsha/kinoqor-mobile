package com.example.kinoqor.ui.cinema

import androidx.lifecycle.*
import com.example.kinoqor.data.local.entity.CinemaEntity
import com.example.kinoqor.data.repository.CinemaRepository
import kotlinx.coroutines.launch

class CinemasViewModel(
    private val repo: CinemaRepository
) : ViewModel() {

    private val _cinemas = MutableLiveData<List<CinemaEntity>>()
    val cinemas: LiveData<List<CinemaEntity>> = _cinemas

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun load() {
        viewModelScope.launch {
            repo.fetchCinemas()
                .onSuccess { _cinemas.value = it }
                .onFailure { _error.value = it.message }
        }
    }
}
