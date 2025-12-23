package com.example.kinoqor.ui.home

import androidx.lifecycle.*
import com.example.kinoqor.data.local.entity.FilmEntity
import com.example.kinoqor.data.repository.FilmRepository
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: FilmRepository
) : ViewModel() {

    private val _films = MutableLiveData<List<FilmEntity>>(emptyList())
    val films: LiveData<List<FilmEntity>> = _films

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun loadFilms() {
        viewModelScope.launch {
            _loading.value = true

            repository.fetchFilms()
                .onSuccess { _films.value = it }
                .onFailure { _error.value = it.message }

            _loading.value = false
        }
    }
}
