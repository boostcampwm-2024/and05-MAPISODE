package com.boostcamp.mapisode.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

internal val LocalMapisodeColorScheme = staticCompositionLocalOf { lightColorScheme }
internal val LocalMapisodeTypography = staticCompositionLocalOf { AppTypography }

object MapisodeTheme {
	val colorScheme: CustomColorScheme
		@Composable @ReadOnlyComposable get() = LocalMapisodeColorScheme.current
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
			// 시스템 status bar 는 투명 (기본값)
			window.navigationBarColor = colors.accentSelected.toArgb()
			WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
				!darkTheme
		}
	}

	CompositionLocalProvider(
		LocalMapisodeColorScheme provides colors,
		LocalMapisodeTypography provides AppTypography,
		content = content,
	)
}
