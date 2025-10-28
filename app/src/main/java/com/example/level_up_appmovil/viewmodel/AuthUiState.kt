package com.example.level_up_appmovil.viewmodel

import java.time.LocalDate

data class AuthUiState(
    // Common
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    // Login
    val isLoginSuccessful: Boolean = false,
    val loginEmail: String = "",
    val loginPass: String = "",

    // Registration
    val isRegistrationSuccessful: Boolean = false,
    val regEmail: String = "",
    val regPass: String = "",
    val regConfirmPass: String = "",
    val birthDate: LocalDate? = null,
    val showDatePicker: Boolean = false
)
