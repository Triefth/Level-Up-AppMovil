package com.example.level_up_appmovil.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.model.User
import com.example.level_up_appmovil.viewmodel.PerfilUiState
import com.example.level_up_appmovil.viewmodel.PerfilViewModel

@Composable
fun PerfilRoute(
    onLogout: () -> Unit,
    onNavigateBack: () -> Unit,
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val uiState by perfilViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedOut) {
        if (uiState.isLoggedOut) {
            onLogout()
            perfilViewModel.onLogoutHandled()
        }
    }

    PerfilScreen(
        uiState = uiState,
        onLogoutClick = { perfilViewModel.logout() },
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    uiState: PerfilUiState,
    onLogoutClick: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading && uiState.user == null) {
                CircularProgressIndicator()
            } else if (uiState.user != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = uiState.user.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.user.email,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    if (uiState.isLoading && uiState.user != null) { // Show loading for logout
                        CircularProgressIndicator()
                    } else {
                        Button(
                            onClick = onLogoutClick,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cerrar Sesión", color = Color.White)
                        }
                    }
                }
            } else if (uiState.error != null) {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview_Content() {
    PerfilScreen(
        uiState = PerfilUiState(user = User("Dann", "dann.ejemplo@duocuc.cl")),
        onLogoutClick = {},
        onNavigateBack = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview_Loading() {
    PerfilScreen(
        uiState = PerfilUiState(isLoading = true),
        onLogoutClick = {},
        onNavigateBack = {}
    )
}
