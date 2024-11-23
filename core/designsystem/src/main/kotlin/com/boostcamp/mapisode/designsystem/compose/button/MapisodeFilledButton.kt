package com.boostcamp.mapisode.designsystem.compose.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.MapisodeTextStyle
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeFilledButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	text: String,
	disabledText: String = text,
	textStyle: MapisodeTextStyle = MapisodeTheme.typography.titleLarge,
	enabled: Boolean = true,
	@DrawableRes leftIcon: Int? = null,
	@DrawableRes rightIcon: Int? = null,
	showRipple: Boolean = false,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
	MapisodeButton(
		onClick = onClick,
		modifier = Modifier
			.then(modifier)
			.widthIn(320.dp)
			.heightIn(52.dp),
		backgroundColors = if (enabled) {
			MapisodeTheme.colorScheme.filledButtonEnableBackground
		} else {
			MapisodeTheme.colorScheme.filledButtonDisableBackground
		},
		contentColor = MapisodeTheme.colorScheme.filledButtonContent,
		enabled = enabled,
		showBorder = false,
		showRipple = showRipple,
		interactionSource = interactionSource,
		rounding = 8.dp,
		contentPadding = PaddingValues(horizontal = 16.dp),
	) {
		leftIcon?.let { icon ->
			MapisodeIcon(
				id = icon,
				iconSize = IconSize.Medium,
			)
			Spacer(modifier = Modifier.width(8.dp))
		}

		MapisodeText(
			text = if (enabled) text else disabledText,
			color = MapisodeTheme.colorScheme.filledButtonContent,
			style = textStyle,
		)

		leftIcon ?: rightIcon?.let { icon ->
			Spacer(modifier = Modifier.width(8.dp))
			MapisodeIcon(
				id = icon,
				iconSize = IconSize.Medium,
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun MapisodeFilledButtonPreview() {
	Column(
		modifier = Modifier.padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {
		MapisodeFilledButton(
			onClick = { },
			text = "활성화 버튼",
		)

		MapisodeFilledButton(
			onClick = { },
			text = "비활성화 버튼",
			enabled = false,
		)
	}
}
