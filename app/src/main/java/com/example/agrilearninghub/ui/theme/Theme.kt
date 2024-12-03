package com.example.agrilearninghub.ui.theme

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme =
    darkColorScheme(
        primary = Color(0xFF97D945),
        onPrimary = Color(0xFF1F3700),
        primaryContainer = Color(0xFF4F8200),
        onPrimaryContainer = Color(0xFFFFFFFF),
        secondary = Color(0xFFB1D188),
        onSecondary = Color(0xFF1F3700),
        secondaryContainer = Color(0xFF2C460C),
        onSecondaryContainer = Color(0xFFBDDE94),
        tertiary = Color(0xFF5DDCB0),
        onTertiary = Color(0xFF003828),
        tertiaryContainer = Color(0xFF008564),
        onTertiaryContainer = Color(0xFFFFFFFF),
        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),
        background = Color(0xFF11150B),
        onBackground = Color(0xFFE0E4D4),
        surface = Color(0xFF11150B),
        onSurface = Color(0xFFE0E4D4),
        surfaceVariant = Color(0xFF424937),
        onSurfaceVariant = Color(0xFFC2CAB2),
        outline = Color(0xFF8C947E),
        outlineVariant = Color(0xFF424937),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFFE0E4D4),
        inverseOnSurface = Color(0xFF2E3227),
        inversePrimary = Color(0xFF3F6900),
        surfaceDim = Color(0xFF11150B),
        surfaceBright = Color(0xFF363B2F),
        surfaceContainerLowest = Color(0xFF0B0F07),
        surfaceContainerLow = Color(0xFF191D13),
        surfaceContainer = Color(0xFF1D2117),
        surfaceContainerHigh = Color(0xFF272B21),
        surfaceContainerHighest = Color(0xFF32362B),
    )

private val lightColorScheme =
    lightColorScheme(
        primary = Color(0xFF3F6900),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFF6FAE19),
        onPrimaryContainer = Color(0xFF071200),
        secondary = Color(0xFF4B662A),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFFD0F2A5),
        onSecondaryContainer = Color(0xFF385217),
        tertiary = Color(0xFF006C50),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFF24B087),
        onTertiaryContainer = Color(0xFF00110A),
        error = Color(0xFFBA1A1A),
        onError = Color(0xFFFFFFFF),
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF410002),
        background = Color(0xFFF7FBEA),
        onBackground = Color(0xFF191D13),
        surface = Color(0xFFF7FBEA),
        onSurface = Color(0xFF191D13),
        surfaceVariant = Color(0xFFDEE6CD),
        onSurfaceVariant = Color(0xFF424937),
        outline = Color(0xFF727A66),
        outlineVariant = Color(0xFFC2CAB2),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFF2E3227),
        inverseOnSurface = Color(0xFFEFF3E2),
        inversePrimary = Color(0xFF97D945),
        surfaceDim = Color(0xFFD8DCCC),
        surfaceBright = Color(0xFFF7FBEA),
        surfaceContainerLowest = Color(0xFFFFFFFF),
        surfaceContainerLow = Color(0xFFF2F5E5),
        surfaceContainer = Color(0xFFECF0DF),
        surfaceContainerHigh = Color(0xFFE6EADA),
        surfaceContainerHighest = Color(0xFFE0E4D4),
    )

@Composable
fun AgriLearningHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> darkColorScheme
            else -> lightColorScheme
        }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            window.statusBarColor = colorScheme.primary.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}