package com.example.level_up_appmovil.viewmodel

import com.example.level_up_appmovil.model.User

data class PerfilUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val isLoggedOut: Boolean = false,
    val error: String? = null
)
