package com.example.level_up_appmovil.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.viewmodel.AuthUiState
import com.example.level_up_appmovil.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val uiState by authViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LoginScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onEmailChange = { authViewModel.onLoginEmailChange(it) },
        onPasswordChange = { authViewModel.onLoginPassChange(it) },
        onLoginClick = { authViewModel.onLoginClick() },
        onRegisterClick = onRegisterClick
    )

    // Handle navigation side-effect
    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            onLoginSuccess()
            authViewModel.resetLoginState() // Reset state to avoid re-triggering
        }
    }

    // Handle error messages
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                authViewModel.dismissError() // Dismiss error after showing
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    uiState: AuthUiState,
    snackbarHostState: SnackbarHostState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val backgroundColor = Color(0xFFFCFCFD)
    val primaryTextColor = Color(0xFF1E90FF)
    val accentColor = Color(0xFF39FF14)

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
                    text = "INICIAR SESIÓN",
                    color = primaryTextColor,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = uiState.loginEmail,
                    onValueChange = onEmailChange,
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.errorMessage != null
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.loginPass,
                    onValueChange = onPasswordChange,
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = uiState.errorMessage != null
                )
                Spacer(modifier = Modifier.height(32.dp))

                if (uiState.isLoading) {
                    CircularProgressIndicator(color = accentColor)
                } else {
                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                    ) {
                        Text("Entrar", color = primaryTextColor)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onRegisterClick) {
                    Text("¿No tienes cuenta? Regístrate", color = accentColor)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = AuthUiState(isLoading = false, errorMessage = null),
        snackbarHostState = remember { SnackbarHostState() },
        onEmailChange = {},
        onPasswordChange = {},
        onLoginClick = {},
        onRegisterClick = {}
    )
}
