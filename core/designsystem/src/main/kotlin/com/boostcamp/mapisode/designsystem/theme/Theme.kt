package com.boostcamp.mapisode.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LocalMapisodeColorScheme = staticCompositionLocalOf { lightColorScheme }
internal val LocalMapisodeTypography = staticCompositionLocalOf { AppTypography }
internal val LocalMapisodeContentColor = compositionLocalOf { lightColorScheme.textContent }
internal val LocalMapisodeContentAlpha = compositionLocalOf { 1f }

private fun CustomColorScheme.pressedColorFor(color: Color): Color = when (color) {
	navBackground -> dialogBackground
	dialogBackground -> navBackground
	textFieldBackground -> chipStroke
	chipStroke -> textFieldBackground
	else -> color
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
	fun pressedColorFor(color: Color): Color = colorScheme.pressedColorFor(color)
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
}
