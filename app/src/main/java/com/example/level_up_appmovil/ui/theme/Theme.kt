package com.example.level_up_appmovil.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta de colores oscuros para Level-Up Gamer
private val LevelUpColorScheme = darkColorScheme(
    primary = ElectricBlue,       // Azul Eléctrico para elementos principales
    secondary = NeonGreen,        // Verde Neón para acentos y acciones secundarias
    background = Color.Black,       // Fondo principal negro
    surface = Color.Black,        // Superficies sobre el fondo
    onPrimary = Color.Black,        // Texto/iconos sobre el color primario (ej. en botones azules)
    onSecondary = Color.Black,      // Texto/iconos sobre el color secundario (ej. en botones verdes)
    onBackground = Color.White,     // Texto/iconos sobre el fondo negro
    onSurface = Color.White,        // Texto/iconos sobre las superficies
    surfaceVariant = LightGray,     // Color para variantes de superficie, como tarjetas
    onSurfaceVariant = Color.Black    // Texto sobre las variantes de superficie
)

@Composable
fun LevelUpAppMovilTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LevelUpColorScheme,
        typography = Typography, // La tipografía que definimos en Type.kt
        content = content
    )
}
