package com.boostcamp.mapisode.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
data class MaruBuriTypography(

	// display styles
	val displayLarge: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 57.sp,
	),
	val displayMedium: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 45.sp,
	),
	val displaySmall: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 36.sp,
	),

	// headline styles
	val headlineLarge: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 32.sp,
	),
	val headlineMedium: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.SemiBold,
		fontSize = 28.sp,
	),
	val headlineSmall: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.SemiBold,
		fontSize = 24.sp,
	),

	// title styles
	val titleLarge: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 22.sp,
	),
	val titleMedium: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
	),
	val titleSmall: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp,
	),

	// body styles
	val bodyLarge: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
	),
	val bodyMedium: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp,
	),
	val bodySmall: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Light,
		fontSize = 12.sp,
	),

	// label styles
	val labelLarge: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp,
	),
	val labelMedium: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 12.sp,
	),
	val labelSmall: TextStyle = TextStyle(
		fontFamily = maruBuriFontFamily,
		fontWeight = FontWeight.ExtraLight,
		fontSize = 10.sp,
	),
)

// Typography 인스턴스
val AppTypography = MaruBuriTypography()
