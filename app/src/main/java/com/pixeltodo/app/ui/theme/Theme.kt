package com.pixeltodo.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PixelColorScheme = darkColorScheme(
    primary = PixelBlue,
    secondary = PixelYellow,
    tertiary = PixelPurple,
    background = PixelBackground,
    surface = PixelCardBackground,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = PixelText,
    onSurface = PixelText,
    error = PixelRed,
    onError = Color.White
)

@Composable
fun PixelTodoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PixelColorScheme,
        content = content
    )
}