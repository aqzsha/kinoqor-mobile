package com.example.kinoqor.ui.auth

import androidx.lifecycle.*
import com.example.kinoqor.data.local.AuthPreferences
import com.example.kinoqor.data.repository.AuthRepository
import kotlinx.coroutines.launch

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val token: String? = null) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class AuthViewModel(
    private val repository: AuthRepository,
    private val prefs: AuthPreferences
) : ViewModel() {

    private val _state = MutableLiveData<LoginUiState>(LoginUiState.Idle)
    val state: LiveData<LoginUiState> = _state

    var tempToken: String? = null
        private set

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _state.value = LoginUiState.Error("Email and password required")
            return
        }

        viewModelScope.launch {
            _state.value = LoginUiState.Loading
            val result = repository.login(email, password)
            result.onSuccess {
                prefs.saveToken(it.token)
                _state.value = LoginUiState.Success(it.token)
            }.onFailure {
                _state.value = LoginUiState.Error(it.message ?: "Unknown error")
            }
        }
    }

    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _state.value = LoginUiState.Error("Email and password required")
            return
        }

        viewModelScope.launch {
            _state.value = LoginUiState.Loading
            val result = repository.register(email, password)
            result.onSuccess {
                prefs.saveToken(it.token)
                _state.value = LoginUiState.Success(it.token)
            }.onFailure {
                _state.value = LoginUiState.Error(it.message ?: "Unknown error")
            }
        }
    }

    fun forgotPassword(email: String) {
        if (email.isBlank()) {
            _state.value = LoginUiState.Error("Email required")
            return
        }

        viewModelScope.launch {
            _state.value = LoginUiState.Loading
            val result = repository.forgotPassword(email)
            result.onSuccess {
                _state.value = LoginUiState.Success(null) // no token returned for forgot
            }.onFailure {
                _state.value = LoginUiState.Error(it.message ?: "Failed to send reset email")
            }
        }
    }

    fun verifyPin(email: String, pinCode: String) {
        if (email.isBlank() || pinCode.length != 6) {
            _state.value = LoginUiState.Error("Email and 6-digit code required")
            return
        }

        viewModelScope.launch {
            _state.value = LoginUiState.Loading
            val result = repository.verifyPin(email, pinCode) // <-- must return Result<String>
            result.onSuccess { token ->
                tempToken = token
                _state.value = LoginUiState.Success(null) // use generic Success signal
            }.onFailure {
                _state.value = LoginUiState.Error(it.message ?: "Invalid verification code")
            }
        }
    }

    fun resetPassword(email: String, token: String, pass: String, confirm: String) {
        if (pass.isBlank() || confirm.isBlank()) {
            _state.value = LoginUiState.Error("All fields required")
            return
        }
        if (pass != confirm) {
            _state.value = LoginUiState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            _state.value = LoginUiState.Loading

            val result = repository.resetPassword(email, token, pass, confirm)
            result.onSuccess {
                _state.value = LoginUiState.Success(null)
            }.onFailure {
                _state.value = LoginUiState.Error(it.message ?: "Failed to reset password")
            }
        }
    }

    fun clearTempToken() {
        tempToken = null
    }

    fun getSavedToken(): String? = prefs.getToken()
}
