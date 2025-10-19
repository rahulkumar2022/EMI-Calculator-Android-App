package com.synergeticsciences.emicalculator.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val FuturisticDarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = DarkBackground,
    primaryContainer = CyanAccent,
    onPrimaryContainer = TextPrimary,
    
    secondary = PurpleAccent,
    onSecondary = TextPrimary,
    secondaryContainer = NeonPurple,
    onSecondaryContainer = TextPrimary,
    
    tertiary = NeonPink,
    onTertiary = TextPrimary,
    
    background = DarkBackground,
    onBackground = TextPrimary,
    
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkCard,
    onSurfaceVariant = TextSecondary,
    
    surfaceTint = NeonCyan,
    inverseSurface = TextPrimary,
    inverseOnSurface = DarkBackground,
    
    error = ErrorRed,
    onError = TextPrimary,
    errorContainer = ErrorRed,
    onErrorContainer = TextPrimary,
    
    outline = TextTertiary,
    outlineVariant = DarkElevated,
    
    scrim = Color.Black.copy(alpha = 0.5f)
)

@Composable
fun EMICalculatorTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = FuturisticDarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkBackground.toArgb()
            window.navigationBarColor = DarkBackground.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}