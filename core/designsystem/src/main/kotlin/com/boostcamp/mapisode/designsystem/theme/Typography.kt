package com.boostcamp.mapisode.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.takeOrElse
import com.boostcamp.mapisode.designsystem.R

// FontFamily 정의
val maruBuriFontFamily = FontFamily(
	Font(R.font.maru_buri_bold, FontWeight.Bold),
	Font(R.font.maru_buri_semi_bold, FontWeight.SemiBold),
	Font(R.font.maru_buri_regular, FontWeight.Normal),
	Font(R.font.maru_buri_light, FontWeight.Light),
	Font(R.font.maru_buri_extra_light, FontWeight.ExtraLight),
)

@Immutable
data class MapisodeTextStyle(
	val fontFamily: FontFamily = maruBuriFontFamily,
	val fontWeight: FontWeight = FontWeight.Medium,
	val fontSize: Dp = Dp.Unspecified,
	val lineHeight: Dp = Dp.Unspecified,
	val color: Color = Color.Unspecified,
	val textAlign: TextAlign = TextAlign.Start,
) {
	@Composable
	fun toTextStyle() = TextStyle(
		fontFamily = fontFamily,
		fontWeight = fontWeight,
		fontSize = if (fontSize != Dp.Unspecified) {
			with(LocalDensity.current) { fontSize.toSp() }
		} else {
			TextUnit.Unspecified
		},
		lineHeight = if (lineHeight != Dp.Unspecified) {
			with(LocalDensity.current) { lineHeight.toSp() }
		} else {
			TextUnit.Unspecified
		},
		color = color,
		textAlign = textAlign,
	)

	fun merge(other: MapisodeTextStyle?): MapisodeTextStyle {
		if (other == null || other == Default) return this
		return MapisodeTextStyle(
			fontFamily = other.fontFamily,
			fontWeight = other.fontWeight,
			fontSize = this.fontSize.takeOrElse { other.fontSize },
			lineHeight = this.lineHeight.takeOrElse { other.lineHeight },
			color = this.color.takeOrElse { other.color },
			textAlign = other.textAlign,
		)
	}

	companion object {
		@Stable
		val Default = MapisodeTextStyle()
	}
}

@Immutable
data class MaruBuriTypography(

	// display styles
	val displayLarge: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Normal,
		fontSize = 57.dp,
		lineHeight = 64.dp,
	),
	val displayMedium: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Normal,
		fontSize = 45.dp,
		lineHeight = 52.dp,
	),
	val displaySmall: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Normal,
		fontSize = 36.dp,
		lineHeight = 40.dp,
	),

	// headline styles
	val headlineLarge: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Bold,
		fontSize = 32.dp,
		lineHeight = 36.dp,
	),
	val headlineMedium: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.SemiBold,
		fontSize = 28.dp,
		lineHeight = 32.dp,
	),
	val headlineSmall: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.SemiBold,
		fontSize = 24.dp,
		lineHeight = 28.dp,
	),

	// title styles
	val titleLarge: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Bold,
		fontSize = 22.dp,
		lineHeight = 26.dp,
	),
	val titleMedium: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Normal,
		fontSize = 16.dp,
		lineHeight = 20.dp,
	),
	val titleSmall: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Medium,
		fontSize = 14.dp,
		lineHeight = 18.dp,
	),

	// body styles
	val bodyLarge: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Normal,
		fontSize = 16.dp,
		lineHeight = 20.dp,
	),
	val bodyMedium: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Normal,
		fontSize = 14.dp,
		lineHeight = 18.dp,
	),
	val bodySmall: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Light,
		fontSize = 12.dp,
		lineHeight = 16.dp,
	),

	// label styles
	val labelLarge: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Medium,
		fontSize = 14.dp,
		lineHeight = 18.dp,
	),
	val labelMedium: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.Medium,
		fontSize = 12.dp,
		lineHeight = 16.dp,
	),
	val labelSmall: MapisodeTextStyle = MapisodeTextStyle(
		fontWeight = FontWeight.ExtraLight,
		fontSize = 10.dp,
		lineHeight = 14.dp,
	),
)

// Typography 인스턴스
val AppTypography = MaruBuriTypography()
