package com.example.level_up_appmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up_appmovil.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Simulate network delay
            delay(1500)

            val mockUser = User(
                name = "Dann",
                email = "dann.ejemplo@duocuc.cl"
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    user = mockUser
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Simulate logout process
            delay(1000)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isLoggedOut = true
                )
            }
        }
    }

    fun onLogoutHandled() {
        _uiState.update { it.copy(isLoggedOut = false) }
    }
}
