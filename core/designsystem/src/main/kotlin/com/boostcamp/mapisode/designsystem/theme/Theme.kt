package com.boostcamp.mapisode.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

internal val LocalMapisodeColorScheme = staticCompositionLocalOf { lightColorScheme }
internal val LocalMapisodeTypography = staticCompositionLocalOf { AppTypography }
internal val LocalMapisodeLightContentColor = compositionLocalOf { lightColorScheme.textContent }
internal val LocalMapisodeDarkContentColor = compositionLocalOf { darkColorScheme.textContent }
internal val LocalMapisodeIconColor = compositionLocalOf { Color.Unspecified }

object MapisodeTheme {
	val colorScheme: CustomColorScheme
		@Composable @ReadOnlyComposable
		get() = LocalMapisodeColorScheme.current

	val typography: MaruBuriTypography
		@Composable @ReadOnlyComposable
		get() = LocalMapisodeTypography.current
}

@Composable
fun MapisodeTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit,
) {
	val colors = if (!darkTheme) lightColorScheme else darkColorScheme

	CompositionLocalProvider(
		LocalMapisodeColorScheme provides colors,
		LocalMapisodeTypography provides AppTypography,
		content = content,
	)

	val view = LocalView.current
	if (!view.isInEditMode) {
		val window = (view.context as Activity).window
		val insetsController = WindowCompat.getInsetsController(window, view)
		insetsController.isAppearanceLightNavigationBars = !darkTheme
		insetsController.isAppearanceLightStatusBars = !darkTheme
	}
}
