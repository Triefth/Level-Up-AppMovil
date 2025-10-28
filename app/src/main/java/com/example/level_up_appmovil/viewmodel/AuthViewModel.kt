package com.example.level_up_appmovil.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.Period

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    // --- Event Handlers for UI ---

    fun onLoginEmailChange(email: String) {
        _uiState.update { it.copy(loginEmail = email) }
    }

    fun onLoginPassChange(pass: String) {
        _uiState.update { it.copy(loginPass = pass) }
    }

    fun onRegEmailChange(email: String) {
        _uiState.update { it.copy(regEmail = email) }
    }

    fun onRegPassChange(pass: String) {
        _uiState.update { it.copy(regPass = pass) }
    }

    fun onRegConfirmPassChange(pass: String) {
        _uiState.update { it.copy(regConfirmPass = pass) }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(birthDate = date, showDatePicker = false) }
    }

    fun showDatePicker(show: Boolean) {
        _uiState.update { it.copy(showDatePicker = show) }
    }

    fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // --- Business Logic Actions ---

    @RequiresApi(Build.VERSION_CODES.O)
    fun onRegisterClick() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        val state = _uiState.value

        if (state.regPass != state.regConfirmPass) {
            _uiState.update { it.copy(isLoading = false, errorMessage = "Las contraseñas no coinciden") }
            return
        }

        if (!isOver18(state.birthDate)) {
            _uiState.update { it.copy(isLoading = false, errorMessage = "Debes ser mayor de 18 años") }
            return
        }
        
        // TODO: Here you would make the actual API call to your backend to register the user.
        // For now, we'll simulate a success.

        _uiState.update {
            it.copy(
                isLoading = false,
                isRegistrationSuccessful = true
            )
        }
    }

    fun onLoginClick() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        val state = _uiState.value

        if (state.loginEmail.isBlank() || state.loginPass.isBlank()) {
             _uiState.update { it.copy(isLoading = false, errorMessage = "Correo y contraseña no pueden estar vacíos") }
            return
        }

        // TODO: Here you would make the actual API call to your backend to log in the user.
        // For now, we'll simulate a success.

        _uiState.update {
            it.copy(
                isLoading = false,
                isLoginSuccessful = true
            )
        }
    }
    
    fun resetLoginState() {
        _uiState.update { it.copy(isLoginSuccessful = false, errorMessage = null) }
    }

    fun resetRegistrationState() {
        _uiState.update { it.copy(isRegistrationSuccessful = false, errorMessage = null) }
    }

    // --- Private Helper Functions ---

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isOver18(birthDate: LocalDate?): Boolean {
        if (birthDate == null) return false
        return Period.between(birthDate, LocalDate.now()).years >= 18
    }

    private fun isDuocEmail(email: String): Boolean {
        return email.endsWith("@duoc.cl", ignoreCase = true) ||
                email.endsWith("@duocuc.cl", ignoreCase = true)
    }
}
