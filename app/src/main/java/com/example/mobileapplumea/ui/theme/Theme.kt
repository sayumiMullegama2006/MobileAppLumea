package com.example.mobileapplumea.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.core.view.WindowCompat
import com.example.mobileapplumea.R // Make sure this import is there

//In your Theme.kt or a separate file
val MyStylishFontFamily = FontFamily(Font(R.font.my_stylish_font))
private val DarkColorScheme = darkColorScheme(
    primary = LumeaPinkDarkPrimary, // This is explicitly set as the primary color for dark mode
    onPrimary = LumeaOnPrimaryDark,
    secondary = LumeaPinkDarkAccent,
    tertiary = LumeaPinkDarkAccent,
    background = LumeaBackgroundDark,
    onBackground = LumeaOnBackgroundDark,
    surface = LumeaSurfaceDark,
    onSurface = LumeaOnSurfaceDark,
)

private val LightColorScheme = lightColorScheme(
    primary = LumeaPinkLight, // This is explicitly set as the primary color for light mode
    onPrimary = LumeaOnPrimaryLight,
    secondary = LumeaPinkDark,
    tertiary = LumeaPinkDark,
    background = LumeaBackgroundLight,
    onBackground = LumeaOnBackgroundLight,
    surface = LumeaSurfaceLight,
    onSurface = LumeaOnSurfaceLight,
)

@Composable
fun MobileAppLumeaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // Set to false if you don't want dynamic colors on Android 12+
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb() // Status bar color
            // Use isAppearanceLightStatusBars for light mode on status bar content
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}