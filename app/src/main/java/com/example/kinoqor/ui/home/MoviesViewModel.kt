package com.example.kinoqor.ui.home

import androidx.lifecycle.*
import com.example.kinoqor.data.remote.dto.FilmDto
import com.example.kinoqor.data.repository.FilmRepository
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: FilmRepository) : ViewModel() {

    private val _films = MutableLiveData<List<FilmDto>>(emptyList())
    val films: LiveData<List<FilmDto>> = _films

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    init {
        loadFilms()
    }

    fun loadFilms() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            val result = repository.fetchFilms()
            result.onSuccess {
                _films.value = it
            }.onFailure {
                _error.value = it.message ?: "Unknown error"
            }
            _loading.value = false
        }
    }
}

class MoviesViewModelFactory(private val repository: FilmRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MoviesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
