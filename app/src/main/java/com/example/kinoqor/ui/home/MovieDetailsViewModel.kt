package com.example.kinoqor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoqor.data.local.entity.FilmEntity
import com.example.kinoqor.data.repository.FilmRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repo: FilmRepository
) : ViewModel() {

    private val _film = MutableLiveData<FilmEntity>()
    val film: LiveData<FilmEntity> = _film

    fun load(id: Long) {
        viewModelScope.launch {
            repo.getFilmDetails(id)
                .onSuccess { _film.value = it }
        }
    }
}
