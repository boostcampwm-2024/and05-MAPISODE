package com.boostcamp.mapisode.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorScheme(
	val primaryText: Color,
	val accentIcon: Color,
	val accentSelected: Color,
	val accentStroke: Color,
	val chipIconFirst: Color,
	val chipIconSecond: Color,
	val chipIconThird: Color,
	val backgroundChip: Color,
	val errorText: Color,
	val secondaryText: Color,
	val hintStroke: Color,
	val bodyText: Color,
	val background: Color,
	val foreground: Color,
	val systemNavBar: Color,
)

// Light Theme Color Scheme
val lightColorScheme = CustomColorScheme(
	primaryText = Primary90,
	accentIcon = Primary50,
	accentSelected = Primary30,
	accentStroke = Primary20,
	chipIconFirst = Tertiary10,
	chipIconSecond = Error10,
	chipIconThird = Primary10,
	backgroundChip = Neutral20,
	errorText = Error30,
	secondaryText = Neutral90,
	hintStroke = Neutral60,
	bodyText = Neutral80,
	background = Neutral10,
	foreground = Neutral110,
	systemNavBar = Neutral20,
)

// Dark Theme Color Scheme
val darkColorScheme = CustomColorScheme(
	primaryText = Primary10,
	accentIcon = Primary40,
	accentSelected = Primary60,
	accentStroke = Primary70,
	chipIconFirst = Tertiary20,
	chipIconSecond = Error40,
	chipIconThird = Primary90,
	backgroundChip = Primary80,
	errorText = Error20,
	secondaryText = Neutral40,
	hintStroke = Neutral70,
	bodyText = Neutral50,
	background = Neutral110,
	foreground = Neutral10,
	systemNavBar = Neutral100,
)
