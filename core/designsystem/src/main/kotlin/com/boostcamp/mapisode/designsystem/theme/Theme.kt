package com.boostcamp.mapisode.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

internal val LocalMapisodeColorScheme = staticCompositionLocalOf { lightColorScheme }
internal val LocalMapisodeTypography = staticCompositionLocalOf { AppTypography }
internal val LocalMapisodeContentColor = compositionLocalOf { lightColorScheme.foreground }
internal val LocalMapisodeContentAlpha = compositionLocalOf { 1f }

private fun CustomColorScheme.pressedColorFor(color: Color): Color {
	return when (color) {
		accentSelected -> background
		background -> accentSelected
		hintStroke -> secondaryText
		secondaryText -> hintStroke
		else -> color
	}
}

object MapisodeTheme {
	val colorScheme: CustomColorScheme
		@Composable @ReadOnlyComposable
		get() = LocalMapisodeColorScheme.current

	val typography: MaruBuriTypography
		@Composable @ReadOnlyComposable
		get() = LocalMapisodeTypography.current

	@Composable
	@ReadOnlyComposable
	fun pressedColorFor(color: Color): Color {
		return colorScheme.pressedColorFor(color)
	}
}

@Composable
fun MapisodeTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit,
) {
	val colors = if (!darkTheme) lightColorScheme else darkColorScheme

	val view = LocalView.current
	if (!view.isInEditMode) {
		SideEffect {
			val window = (view.context as Activity).window
			window.statusBarColor = colors.systemNavBar.toArgb()
			window.navigationBarColor = colors.systemNavBar.toArgb()

			val insetsController = WindowCompat.getInsetsController(window, view)
			insetsController.isAppearanceLightNavigationBars = !darkTheme
			insetsController.isAppearanceLightStatusBars = !darkTheme
		}
	}

	CompositionLocalProvider(
		LocalMapisodeColorScheme provides colors,
		LocalMapisodeTypography provides AppTypography,
		content = content,
	)
}
