package com.petsit.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PetSitColorScheme = lightColorScheme(
    primary = Color(0xFF0F6A67),
    secondary = Color(0xFF0C5553),
    primaryContainer = Color(0xFFE9F3F1),
    background = Color(0xFFF7FAF9),
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color(0xFF263536),
    onSurface = Color(0xFF263536)
)

@Composable
fun PetSitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PetSitColorScheme,
        typography = Typography,
        content = content
    )
}
