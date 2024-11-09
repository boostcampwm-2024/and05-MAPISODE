package com.boostcamp.mapisode.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorScheme(
	val primaryText: Color,
	val accentIcon: Color,
	val accentSelected: Color,
	val accentStroke: Color,
	val backgroundChip: Color,
	val errorText: Color,
	val secondaryText: Color,
	val hintStroke: Color,
	val bodyText: Color,
	val background: Color,
	val foreground: Color,
)

// Light Theme Color Scheme
val lightColorScheme = CustomColorScheme(
	primaryText = ColorPrimaryText,
	accentIcon = ColorAccentIcon,
	accentSelected = ColorAccentSelected,
	accentStroke = ColorAccentStroke,
	backgroundChip = ColorBackgroundChip,
	errorText = ColorErrorText,
	secondaryText = ColorSecondaryText,
	hintStroke = ColorHintStroke,
	bodyText = ColorBodyText,
	background = ColorBackground,
	foreground = ColorForeground,
)

// Dark Theme Color Scheme
val darkColorScheme = CustomColorScheme(
	primaryText = ColorPrimaryTextDark,
	accentIcon = ColorAccentIconDark,
	accentSelected = ColorAccentSelectedDark,
	accentStroke = ColorAccentStrokeDark,
	backgroundChip = ColorBackgroundChipDark,
	errorText = ColorErrorTextDark,
	secondaryText = ColorSecondaryTextDark,
	hintStroke = ColorHintStrokeDark,
	bodyText = ColorBodyTextDark,
	background = ColorBackgroundDark,
	foreground = ColorForegroundDark,
)
