package com.example.level_up_appmovil.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.viewmodel.AuthUiState
import com.example.level_up_appmovil.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegistroRoute(
    onRegisterSuccess: () -> Unit,
    onBackToLoginClick: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val uiState by authViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    RegistroScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onEmailChange = { authViewModel.onRegEmailChange(it) },
        onPasswordChange = { authViewModel.onRegPassChange(it) },
        onConfirmPasswordChange = { authViewModel.onRegConfirmPassChange(it) },
        onDateSelected = { authViewModel.onDateSelected(it) },
        onShowDatePicker = { authViewModel.showDatePicker(it) },
        onRegisterClick = { authViewModel.onRegisterClick() },
        onBackToLoginClick = onBackToLoginClick
    )

    LaunchedEffect(uiState.isRegistrationSuccessful) {
        if (uiState.isRegistrationSuccessful) {
            onRegisterSuccess()
            authViewModel.resetRegistrationState()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                authViewModel.dismissError()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    uiState: AuthUiState,
    snackbarHostState: SnackbarHostState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onShowDatePicker: (Boolean) -> Unit,
    onRegisterClick: () -> Unit,
    onBackToLoginClick: () -> Unit
) {
    val backgroundColor = Color(0xFFFCFCFD)
    val primaryTextColor = Color(0xFF1E90FF)
    val accentColor = Color(0xFF39FF14)

    if (uiState.showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
        DatePickerDialog(
            onDismissRequest = { onShowDatePicker(false) },
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val selectedDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                        onDateSelected(selectedDate)
                    }
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { onShowDatePicker(false) }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(backgroundColor)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "REGISTRO",
                    color = primaryTextColor,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = uiState.regEmail,
                    onValueChange = onEmailChange,
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.errorMessage != null
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.birthDate?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Fecha de Nacimiento") },
                    trailingIcon = { Icon(Icons.Default.DateRange, "Select Date") },
                    modifier = Modifier.fillMaxWidth().clickable { onShowDatePicker(true) },
                    isError = uiState.errorMessage?.contains("edad") == true
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.regPass,
                    onValueChange = onPasswordChange,
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = uiState.errorMessage?.contains("contraseñas") == true
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.regConfirmPass,
                    onValueChange = onConfirmPasswordChange,
                    label = { Text("Confirmar Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = uiState.errorMessage?.contains("contraseñas") == true
                )
                Spacer(modifier = Modifier.height(24.dp))

                if (uiState.isLoading) {
                    CircularProgressIndicator(color = accentColor)
                } else {
                    Button(
                        onClick = onRegisterClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                    ) {
                        Text("Registrarse", color = Color.Black)
                    }
                }

                TextButton(onClick = onBackToLoginClick) {
                    Text("¿Ya tienes cuenta? Inicia Sesión", color = Color.Blue)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegistroScreen(
        uiState = AuthUiState(),
        snackbarHostState = remember { SnackbarHostState() },
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onDateSelected = {},
        onShowDatePicker = {},
        onRegisterClick = {},
        onBackToLoginClick = {}
    )
}
