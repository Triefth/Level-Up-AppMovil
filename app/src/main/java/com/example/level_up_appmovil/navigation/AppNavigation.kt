package com.example.level_up_appmovil.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level_up_appmovil.ui.screen.CatalogoRoute
import com.example.level_up_appmovil.ui.screen.LoginRoute
import com.example.level_up_appmovil.ui.screen.PerfilRoute
import com.example.level_up_appmovil.ui.screen.RegistroRoute
import com.example.level_up_appmovil.ui.screen.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {
        composable(AppRoutes.SPLASH) {
            SplashScreen(onTimeout = {
                navController.navigate(AppRoutes.LOGIN) {
                    popUpTo(AppRoutes.SPLASH) { inclusive = true }
                }
            })
        }

        composable(AppRoutes.LOGIN) {
            LoginRoute(
                onLoginSuccess = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(AppRoutes.REGISTER)
                }
            )
        }

        composable(AppRoutes.REGISTER) {
            RegistroRoute(
                onRegisterSuccess = { navController.popBackStack() },
                onBackToLoginClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.HOME) {
            CatalogoRoute(
                onNavigateToPerfil = {
                    navController.navigate(AppRoutes.PERFIL)
                }
            )
        }

        composable(AppRoutes.PERFIL) {
            PerfilRoute(
                onLogout = {
                    navController.navigate(AppRoutes.LOGIN) {
                        // Clear the entire back stack up to the login screen
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
