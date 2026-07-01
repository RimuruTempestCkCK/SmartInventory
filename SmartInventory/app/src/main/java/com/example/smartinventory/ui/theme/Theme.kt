package com.example.smartinventory.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DiscordColorScheme = darkColorScheme(
    primary = DiscordBlurple,
    onPrimary = DiscordInk,
    secondary = DiscordGreen,
    onSecondary = DiscordInkDark,
    tertiary = DiscordMagenta,
    onTertiary = DiscordInk,
    background = DiscordCanvas,
    onBackground = DiscordInk,
    surface = DiscordSurfaceIndigo,
    onSurface = DiscordInk,
    error = Color.Red,
    onError = Color.White
)

// We keep a light version just in case, but Discord style is primarily dark
private val LightColorScheme = lightColorScheme(
    primary = DiscordBlurple,
    onPrimary = Color.White,
    secondary = DiscordGreen,
    onSecondary = DiscordInkDark,
    tertiary = DiscordMagenta,
    background = Color.White,
    surface = Color.White,
    onBackground = DiscordInkDark,
    onSurface = DiscordInkDark
)

@Composable
fun SmartInventoryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color disabled to strictly follow Discord Design
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DiscordColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
