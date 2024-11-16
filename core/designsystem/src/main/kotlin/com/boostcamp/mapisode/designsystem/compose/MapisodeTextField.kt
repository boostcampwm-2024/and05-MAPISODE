package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.theme.MapisodeTextStyle
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodePlaceHolder(value: String) {
	MapisodeText(
		text = value,
		style = MapisodeTheme.typography.labelLarge,
		color = MapisodeTheme.colorScheme.textFieldContent,
	)
}

@Composable
fun MapisodeTextField(
	value: String,
	onValueChange: (String) -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	readOnly: Boolean = false,
	textStyle: MapisodeTextStyle = MapisodeTheme.typography.labelLarge,
	label: @Composable (() -> Unit)? = null,
	placeholder: String,
	leadingIcon: @Composable (() -> Unit)? = null,
	trailingIcon: @Composable (() -> Unit)? = null,
	prefix: @Composable (() -> Unit)? = null,
	suffix: @Composable (() -> Unit)? = null,
	isError: Boolean = false,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	onSubmitInput: (String) -> Unit = {},
	keyboardOptions: KeyboardOptions = KeyboardOptions(
		imeAction = ImeAction.Done,
	),
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	singleLine: Boolean = false,
	maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
	minLines: Int = 1,
	interactionSource: MutableInteractionSource? = null,
	shape: Shape = RoundedCornerShape(8.dp),
	containerColor: Color = MapisodeTheme.colorScheme.textFieldBackground,
	textColor: Color = MapisodeTheme.colorScheme.textFieldContent,
	cursorColor: Color = MapisodeTheme.colorScheme.textFieldStroke,
	errorContainerColor: Color = MapisodeTheme.colorScheme.seeIconColor,
	errorTextColor: Color = MapisodeTheme.colorScheme.seeIconColor,
	strokeColor: Color = MapisodeTheme.colorScheme.textFieldStroke,
	strokeWidth: Dp = 1.dp,
) {
	@Suppress("NAME_SHADOWING")
	val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
	val focusRequester = remember { FocusRequester() }
	val focusManager = LocalFocusManager.current

	Box(
		modifier = modifier
            .width(320.dp)
            .height(42.dp)
            .defaultMinSize(
                minWidth = 320.dp,
                minHeight = 42.dp,
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled && !readOnly,
            ) { }
            .border(strokeWidth, strokeColor, shape)
            .background(if (isError) errorContainerColor else containerColor, shape)
            .padding(vertical = 12.dp, horizontal = 16.dp),
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
		) {
			if (leadingIcon != null) {
				Box(modifier = Modifier.padding(end = 16.dp)) {
					leadingIcon()
				}
			}
			BasicTextField(
				value = value,
				onValueChange = onValueChange,
				modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
				enabled = enabled,
				readOnly = readOnly,
				textStyle = textStyle.copy(color = textColor).toTextStyle(),
				cursorBrush = SolidColor(cursorColor),
				visualTransformation = visualTransformation,
				keyboardOptions = keyboardOptions,
				keyboardActions = KeyboardActions(
					onDone = {
						onSubmitInput(value)
						focusManager.clearFocus()
					},
				),
				interactionSource = interactionSource,
				singleLine = singleLine,
				maxLines = maxLines,
				minLines = minLines,
				decorationBox = { innerTextField ->
					Box(
						modifier = Modifier.fillMaxWidth(),
						contentAlignment = Alignment.CenterStart,
					) {
						if (prefix != null) {
							Box(modifier = Modifier.padding(end = 8.dp)) {
								prefix()
							}
						}

						if (value.isEmpty() && placeholder.isNotBlank()) {
							MapisodePlaceHolder(value = placeholder)
						}

						innerTextField()

						if (suffix != null) {
							Box(modifier = Modifier.padding(start = 8.dp)) {
								suffix()
							}
						}
					}
				},
			)
		}
	}
}

