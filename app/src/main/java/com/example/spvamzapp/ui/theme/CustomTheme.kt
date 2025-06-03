package com.example.spvamzapp.ui.theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val lightSchemeBlue = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkSchemeBlue = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val lightSchemeRed = lightColorScheme(
    primary = primaryLight2,
    onPrimary = onPrimaryLight2,
    primaryContainer = primaryContainerLight2,
    onPrimaryContainer = onPrimaryContainerLight2,
    secondary = secondaryLight2,
    onSecondary = onSecondaryLight2,
    secondaryContainer = secondaryContainerLight2,
    onSecondaryContainer = onSecondaryContainerLight2,
    tertiary = tertiaryLight2,
    onTertiary = onTertiaryLight2,
    tertiaryContainer = tertiaryContainerLight2,
    onTertiaryContainer = onTertiaryContainerLight2,
    error = errorLight2,
    onError = onErrorLight2,
    errorContainer = errorContainerLight2,
    onErrorContainer = onErrorContainerLight2,
    background = backgroundLight2,
    onBackground = onBackgroundLight2,
    surface = surfaceLight2,
    onSurface = onSurfaceLight2,
    surfaceVariant = surfaceVariantLight2,
    onSurfaceVariant = onSurfaceVariantLight2,
    outline = outlineLight2,
    outlineVariant = outlineVariantLight2,
    scrim = scrimLight2,
    inverseSurface = inverseSurfaceLight2,
    inverseOnSurface = inverseOnSurfaceLight2,
    inversePrimary = inversePrimaryLight2,
    surfaceDim = surfaceDimLight2,
    surfaceBright = surfaceBrightLight2,
    surfaceContainerLowest = surfaceContainerLowestLight2,
    surfaceContainerLow = surfaceContainerLowLight2,
    surfaceContainer = surfaceContainerLight2,
    surfaceContainerHigh = surfaceContainerHighLight2,
    surfaceContainerHighest = surfaceContainerHighestLight2,
)

private val darkSchemeRed = darkColorScheme(
    primary = primaryDark2,
    onPrimary = onPrimaryDark2,
    primaryContainer = primaryContainerDark2,
    onPrimaryContainer = onPrimaryContainerDark2,
    secondary = secondaryDark2,
    onSecondary = onSecondaryDark2,
    secondaryContainer = secondaryContainerDark2,
    onSecondaryContainer = onSecondaryContainerDark2,
    tertiary = tertiaryDark2,
    onTertiary = onTertiaryDark2,
    tertiaryContainer = tertiaryContainerDark2,
    onTertiaryContainer = onTertiaryContainerDark2,
    error = errorDark2,
    onError = onErrorDark2,
    errorContainer = errorContainerDark2,
    onErrorContainer = onErrorContainerDark2,
    background = backgroundDark2,
    onBackground = onBackgroundDark2,
    surface = surfaceDark2,
    onSurface = onSurfaceDark2,
    surfaceVariant = surfaceVariantDark2,
    onSurfaceVariant = onSurfaceVariantDark2,
    outline = outlineDark2,
    outlineVariant = outlineVariantDark2,
    scrim = scrimDark2,
    inverseSurface = inverseSurfaceDark2,
    inverseOnSurface = inverseOnSurfaceDark2,
    inversePrimary = inversePrimaryDark2,
    surfaceDim = surfaceDimDark2,
    surfaceBright = surfaceBrightDark2,
    surfaceContainerLowest = surfaceContainerLowestDark2,
    surfaceContainerLow = surfaceContainerLowDark2,
    surfaceContainer = surfaceContainerDark2,
    surfaceContainerHigh = surfaceContainerHighDark2,
    surfaceContainerHighest = surfaceContainerHighestDark2,
)

private val lightSchemeGreen = lightColorScheme(
    primary = primaryLight3,
    onPrimary = onPrimaryLight3,
    primaryContainer = primaryContainerLight3,
    onPrimaryContainer = onPrimaryContainerLight3,
    secondary = secondaryLight3,
    onSecondary = onSecondaryLight3,
    secondaryContainer = secondaryContainerLight3,
    onSecondaryContainer = onSecondaryContainerLight3,
    tertiary = tertiaryLight3,
    onTertiary = onTertiaryLight3,
    tertiaryContainer = tertiaryContainerLight3,
    onTertiaryContainer = onTertiaryContainerLight3,
    error = errorLight3,
    onError = onErrorLight3,
    errorContainer = errorContainerLight3,
    onErrorContainer = onErrorContainerLight3,
    background = backgroundLight3,
    onBackground = onBackgroundLight3,
    surface = surfaceLight3,
    onSurface = onSurfaceLight3,
    surfaceVariant = surfaceVariantLight3,
    onSurfaceVariant = onSurfaceVariantLight3,
    outline = outlineLight3,
    outlineVariant = outlineVariantLight3,
    scrim = scrimLight3,
    inverseSurface = inverseSurfaceLight3,
    inverseOnSurface = inverseOnSurfaceLight3,
    inversePrimary = inversePrimaryLight3,
    surfaceDim = surfaceDimLight3,
    surfaceBright = surfaceBrightLight3,
    surfaceContainerLowest = surfaceContainerLowestLight3,
    surfaceContainerLow = surfaceContainerLowLight3,
    surfaceContainer = surfaceContainerLight3,
    surfaceContainerHigh = surfaceContainerHighLight3,
    surfaceContainerHighest = surfaceContainerHighestLight3,
)

private val darkSchemeGreen = darkColorScheme(
    primary = primaryDark3,
    onPrimary = onPrimaryDark3,
    primaryContainer = primaryContainerDark3,
    onPrimaryContainer = onPrimaryContainerDark3,
    secondary = secondaryDark3,
    onSecondary = onSecondaryDark3,
    secondaryContainer = secondaryContainerDark3,
    onSecondaryContainer = onSecondaryContainerDark3,
    tertiary = tertiaryDark3,
    onTertiary = onTertiaryDark3,
    tertiaryContainer = tertiaryContainerDark3,
    onTertiaryContainer = onTertiaryContainerDark3,
    error = errorDark3,
    onError = onErrorDark3,
    errorContainer = errorContainerDark3,
    onErrorContainer = onErrorContainerDark3,
    background = backgroundDark3,
    onBackground = onBackgroundDark3,
    surface = surfaceDark3,
    onSurface = onSurfaceDark3,
    surfaceVariant = surfaceVariantDark3,
    onSurfaceVariant = onSurfaceVariantDark3,
    outline = outlineDark3,
    outlineVariant = outlineVariantDark3,
    scrim = scrimDark3,
    inverseSurface = inverseSurfaceDark3,
    inverseOnSurface = inverseOnSurfaceDark3,
    inversePrimary = inversePrimaryDark3,
    surfaceDim = surfaceDimDark3,
    surfaceBright = surfaceBrightDark3,
    surfaceContainerLowest = surfaceContainerLowestDark3,
    surfaceContainerLow = surfaceContainerLowDark3,
    surfaceContainer = surfaceContainerDark3,
    surfaceContainerHigh = surfaceContainerHighDark3,
    surfaceContainerHighest = surfaceContainerHighestDark3,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    choice: Int = 1,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme == true -> {
          when (choice) {
              1 -> darkSchemeBlue
              2 -> darkSchemeRed
              else -> darkSchemeGreen
          }
      }
      else -> {
          when (choice) {
              1 -> lightSchemeBlue
              2 -> lightSchemeRed
              else -> lightSchemeGreen
          }
      }
  }

  MaterialTheme(
    colorScheme = colorScheme, typography = AppTypography,
    content = content
  )
}

