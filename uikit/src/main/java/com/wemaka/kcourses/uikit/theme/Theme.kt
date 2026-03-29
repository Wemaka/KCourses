package com.wemaka.kcourses.uikit.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF12B956),
    onPrimary = Color(0xFFF2F2F3),

    primaryContainer = Color(0xFF0F3D25),
    onPrimaryContainer = Color(0xFF7EF7B2),

    secondary = Color(0xFF2E7D5B),
    onSecondary = Color(0xFFE6FFF4),

    secondaryContainer = Color(0xFF32333A),
    onSecondaryContainer = Color(0xFF12B956),

    tertiary = Color(0xFF4BB34B),
    onTertiary = Color(0xFF0A1F0A),

    tertiaryContainer = Color(0xFF1F3A1F),
    onTertiaryContainer = Color(0xFFA6E3A6),

    background = Color(0xFF151515),
    onBackground = Color(0xFFEDEDED),

    surface = Color(0xFF1E1F23),
    onSurface = Color(0xFFEDEDED),

    surfaceVariant = Color(0xFF2A2D33),
    onSurfaceVariant = Color(0xFF9AA0A6),

    surfaceContainer = Color(0xFF24252A),

    outline = Color(0xFF4E555E),
    outlineVariant = Color(0xFF2A2F36),

    error = Color(0xFFFF4D5E),
    onError = Color(0xFFFFFFFF),

    errorContainer = Color(0xFF5C1A1F),
    onErrorContainer = Color(0xFFFFDAD6),

    inverseSurface = Color(0xFFEDEDED),
    inverseOnSurface = Color(0xFF1A1B1F),
    inversePrimary = Color(0xFF12B956)
)

private val LightColorScheme = lightColorScheme(
)

@Composable
fun KCoursesTheme(
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
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}