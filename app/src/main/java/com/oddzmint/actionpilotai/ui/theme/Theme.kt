package com.oddzmint.actionpilotai.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Paper,
    onPrimary = Ink,
    primaryContainer = Paper,
    onPrimaryContainer = Ink,
    secondary = Signal,
    onSecondary = Ink,
    secondaryContainer = PaperDimDark,
    onSecondaryContainer = TextPrimaryDark,
    tertiary = Teal,
    onTertiary = PaperDark,
    background = PaperDark,
    onBackground = TextPrimaryDark,
    surface = PaperDark,
    onSurface = TextPrimaryDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = StoneLight,
    outlineVariant = Stone,
    error = RustDark,
    onError = Ink
)

private val LightColorScheme = lightColorScheme(
    primary = Ink,
    onPrimary = Paper,
    primaryContainer = Ink,
    onPrimaryContainer = Paper,
    secondary = Signal,
    onSecondary = Ink,
    secondaryContainer = PaperDim,
    onSecondaryContainer = Ink,
    tertiary = Teal,
    onTertiary = Paper,
    background = Paper,
    onBackground = TextPrimary,
    surface = Paper,
    onSurface = TextPrimary,
    surfaceVariant = PaperDim,
    onSurfaceVariant = TextSecondary,
    outline = Stone,
    outlineVariant = StoneLight,
    error = Rust,
    onError = Paper


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ActionPilotAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}