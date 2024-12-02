package com.boostcamp.mapisode.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorScheme(
	// Navigation
	val navSelectedItem: Color,
	val navUnselectedItem: Color,
	val navBackground: Color,

	// Top App Bar
	val topAppBarTitle: Color,

	// Circular Indicator
	val circularIndicator: Color,

	// Scaffold
	val scaffoldBackground: Color,

	// TextColoredContainer
	val textColoredContainer: Color,

	// Scrim
	val scrim: Color,

	// Date Picker
	val dateBoxSelectedBackground: Color,
	val dateBoxSelectedText: Color,
	val dateBoxUnselectedText: Color,
	val dateBoxTodayText: Color,
	val dateBoxSundayText: Color,
	val dayOfWeekText: Color,
	val datePickerHeader: Color,
	val datePickerRequest: Color,
	val datePickerDismiss: Color,
	val datePickerBackground: Color,
	val datePickerBorder: Color,

	// Menu
	val menuBackground: Color,
	val menuStroke: Color,
	val menuItemBackground: Color,

	// Tab
	val tabBackground: Color,
	val tabItemBackground: Color,
	val tabItemTextSelected: Color,
	val tabItemTextUnselected: Color,
	val tabItemIcon: Color,
	val tabIndicator: Color,

	// Button
	val filledButtonEnableBackground: Color,
	val filledButtonDisableBackground: Color,
	val filledButtonContent: Color,
	val outlineButtonBackground: Color,
	val outlineButtonStroke: Color,
	val outlineButtonContent: Color,

	// Chip
	val chipUnselectedBackground: Color,
	val chipSelectedBackground: Color,
	val chipText: Color,
	val chipSelectedStroke: Color,
	val chipUnselectedStroke: Color,

	// Text Field
	val textFieldBackground: Color,
	val textFieldContent: Color,
	val textFieldStroke: Color,

	// Tag
	val tagFieldBackground: Color,
	val tagBorder: Color,
	val tagBackground: Color,
	val tagText: Color,

	// Dialog
	val dialogBackground: Color,
	val dialogStroke: Color,
	val dialogDismiss: Color,
	val dialogConfirm: Color,

	// Toast
	val toastBackground: Color,
	val toastStroke: Color,
	val toastContent: Color,

	// Icon Button
	val iconButtonBackground: Color,
	val iconButtonEnabled: Color,
	val iconButtonDisabled: Color,

	// Icon
	val iconColor: Color,
	val eatIconColor: Color,
	val seeIconColor: Color,
	val otherIconColor: Color,

	// Surface
	val surfaceBackground: Color,

	// Divider
	val dividerThinColor: Color,
	val dividerThickColor: Color,

	// Text
	val textBackground: Color,
	val textContent: Color,
	val textStroke: Color,

	// FAB
	val fabBackground: Color,
	val fabContent: Color,

	// System Bar
	val systemBarTransparent: Color,
	val systemBarColored: Color,

	// Checkbox
	val checkboxBackground: Color,
	val checkboxStroke: Color,
)

// Light Theme Color Scheme
val lightColorScheme = CustomColorScheme(
	// Navigation
	navSelectedItem = Primary30,
	navUnselectedItem = Neutral90,
	navBackground = Neutral10,

	// Top App Bar
	topAppBarTitle = Neutral110,

	// Circular Indicator
	circularIndicator = Primary30,

	// Scaffold
	scaffoldBackground = Neutral10,

	// TextColoredContainer
	textColoredContainer = Neutral40.copy(alpha = 0.4f),

	// Scrim
	scrim = Neutral110.copy(alpha = 0.32f),

	// Date Picker
	dateBoxSelectedBackground = Primary30,
	dateBoxSelectedText = Neutral10,
	dateBoxUnselectedText = Neutral80,
	dateBoxTodayText = Neutral100,
	dateBoxSundayText = Error40,
	dayOfWeekText = Neutral100,
	datePickerHeader = Neutral110,
	datePickerRequest = Neutral110,
	datePickerDismiss = Error30,
	datePickerBackground = Neutral10,
	datePickerBorder = Neutral70,

	// Menu
	menuBackground = Neutral10,
	menuStroke = Neutral110,
	menuItemBackground = Neutral10,

	// Tab
	tabBackground = Neutral10,
	tabItemBackground = Neutral10,
	tabItemTextSelected = Neutral110,
	tabItemTextUnselected = Neutral40,
	tabItemIcon = Neutral10,
	tabIndicator = Primary30,

	// Button
	filledButtonEnableBackground = Primary30,
	filledButtonDisableBackground = Neutral60,
	filledButtonContent = Neutral10,
	outlineButtonBackground = Neutral10,
	outlineButtonStroke = Neutral60,
	outlineButtonContent = Neutral70,

	// Chip
	chipUnselectedBackground = Neutral10,
	chipSelectedBackground = Neutral20,
	chipText = Neutral110,
	chipSelectedStroke = Primary30,
	chipUnselectedStroke = Neutral40,

	// Text Field
	textFieldBackground = Color.Unspecified,
	textFieldContent = Neutral60,
	textFieldStroke = Neutral60,

	// Tag
	tagFieldBackground = Neutral10,
	tagBorder = Neutral60,
	tagBackground = Primary20,
	tagText = Neutral70,

	// Dialog
	dialogBackground = Neutral20,
	dialogStroke = Color.Unspecified,
	dialogDismiss = Error30,
	dialogConfirm = Primary80,

	// Toast
	toastBackground = Neutral20,
	toastStroke = Color.Unspecified,
	toastContent = Neutral110,

	// Icon Button
	iconButtonBackground = Neutral10,
	iconButtonEnabled = Neutral110,
	iconButtonDisabled = Neutral50,

	// Icon
	iconColor = Neutral110,
	eatIconColor = Tertiary10,
	seeIconColor = Error10,
	otherIconColor = Primary40,

	// Surface
	surfaceBackground = Neutral10,

	// Divider
	dividerThinColor = Neutral30,
	dividerThickColor = Color.Transparent,

	// Text
	textBackground = Color.Unspecified,
	textContent = Neutral110,
	textStroke = Color.Transparent,

	// FAB
	fabBackground = Primary30,
	fabContent = Primary60,

	// System Bar
	systemBarTransparent = Color.Transparent,
	systemBarColored = Primary10,

	// Checkbox
	checkboxBackground = Primary20.copy(alpha = 0.5f),
	checkboxStroke = Primary50.copy(alpha = 0.5f),
)

// Dark Theme Color Scheme
val darkColorScheme = CustomColorScheme(
	// Navigation
	navSelectedItem = Primary40,
	navUnselectedItem = Neutral40,
	navBackground = Neutral110,

	// Top App Bar
	topAppBarTitle = Neutral10,

	// Circular Indicator
	circularIndicator = Primary40,

	// Scaffold
	scaffoldBackground = Neutral110,

	// TextColoredContainer
	textColoredContainer = Neutral80.copy(alpha = 0.6f),

	// Scrim
	scrim = Neutral10.copy(alpha = 0.32f),

	// Date Picker
	dateBoxSelectedBackground = Primary40,
	dateBoxSelectedText = Neutral10,
	dateBoxUnselectedText = Neutral70,
	dateBoxTodayText = Neutral10,
	dateBoxSundayText = Error10,
	dayOfWeekText = Neutral20,
	datePickerHeader = Neutral10,
	datePickerRequest = Neutral10,
	datePickerDismiss = Error20,
	datePickerBackground = Neutral90,
	datePickerBorder = Neutral70,

	// Menu
	menuBackground = Neutral110,
	menuStroke = Neutral20,
	menuItemBackground = Neutral110,

	// Tab
	tabBackground = Neutral110,
	tabItemBackground = Neutral110,
	tabItemTextSelected = Neutral10,
	tabItemTextUnselected = Neutral90,
	tabItemIcon = Neutral40,
	tabIndicator = Primary30,

	// Button
	filledButtonEnableBackground = Primary50,
	filledButtonDisableBackground = Neutral80,
	filledButtonContent = Neutral110,
	outlineButtonBackground = Neutral110,
	outlineButtonStroke = Neutral40,
	outlineButtonContent = Neutral40,

	// Chip
	chipSelectedBackground = Neutral80,
	chipUnselectedBackground = Neutral100,
	chipText = Neutral10,
	chipSelectedStroke = Primary30,
	chipUnselectedStroke = Neutral40,

	// Text Field
	textFieldBackground = Color.Unspecified,
	textFieldContent = Neutral70,
	textFieldStroke = Neutral70,

	// Tag
	tagFieldBackground = Neutral110,
	tagBorder = Neutral70,
	tagBackground = Primary40,
	tagText = Neutral10,

	// Dialog
	dialogBackground = Neutral100,
	dialogStroke = Color.Unspecified,
	dialogDismiss = Error20,
	dialogConfirm = Primary20,

	// Toast
	toastBackground = Neutral100,
	toastStroke = Color.Unspecified,
	toastContent = Neutral10,

	// Icon Button
	iconButtonBackground = Neutral110,
	iconButtonEnabled = Neutral10,
	iconButtonDisabled = Neutral50,

	// Icon
	iconColor = Neutral10,
	eatIconColor = Tertiary10,
	seeIconColor = Error10,
	otherIconColor = Primary20,

	// Surface
	surfaceBackground = Neutral110,

	// Divider
	dividerThinColor = Neutral40,
	dividerThickColor = Color.Transparent,

	// Text
	textBackground = Color.Unspecified,
	textContent = Neutral10,
	textStroke = Color.Transparent,

	// FAB
	fabBackground = Primary60,
	fabContent = Primary30,

	// System Bar
	systemBarTransparent = Color.Transparent,
	systemBarColored = Primary90,

	// Checkbox
	checkboxBackground = Primary20.copy(alpha = 0.6f),
	checkboxStroke = Neutral40,
)
