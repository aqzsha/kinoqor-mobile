package com.example.kinoqor.ui.auth

import androidx.lifecycle.*
import com.example.kinoqor.data.local.AuthPreferences
import com.example.kinoqor.data.repository.AuthRepository
import kotlinx.coroutines.launch

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val token: String) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class AuthViewModel(
    private val repository: AuthRepository,
    private val prefs: AuthPreferences
) : ViewModel() {

    private val _state = MutableLiveData<LoginUiState>(LoginUiState.Idle)
    val state: LiveData<LoginUiState> = _state

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _state.value = LoginUiState.Error("Email and password required")
            return
        }

        viewModelScope.launch {
            _state.value = LoginUiState.Loading
            val result = repository.login(email, password)
            result
                .onSuccess {
                    prefs.saveToken(it.token)
                    _state.value = LoginUiState.Success(it.token)
                }
                .onFailure {
                    _state.value = LoginUiState.Error(it.message ?: "Unknown error")
                }
        }
    }

    fun getSavedToken(): String? = prefs.getToken()
}