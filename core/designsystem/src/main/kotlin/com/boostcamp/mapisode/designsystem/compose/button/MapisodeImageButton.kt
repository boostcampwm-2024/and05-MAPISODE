package com.boostcamp.mapisode.designsystem.compose.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeImageButton(
	modifier: Modifier = Modifier,
	onClick: () -> Unit,
	text: String,
	enabled: Boolean = true,
	showRipple: Boolean = true,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	imageContent: @Composable () -> Unit,
) {
	MapisodeButton(
		onClick = onClick,
		backgroundColors = MapisodeTheme.colorScheme.outlineButtonBackground,
		contentColor = MapisodeTheme.colorScheme.outlineButtonContent,
		modifier = modifier
			.widthIn(150.dp)
			.heightIn(150.dp),
		enabled = enabled,
		showBorder = true,
		borderColor = MapisodeTheme.colorScheme.outlineButtonStroke,
		showRipple = showRipple,
		interactionSource = interactionSource,
		rounding = 8.dp,
		contentPadding = PaddingValues(16.dp),
	) {
		if (enabled) {
			Column(
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				MapisodeIcon(
					id = R.drawable.ic_image,
					iconSize = IconSize.Large,
					tint = MapisodeTheme.colorScheme.outlineButtonContent,
				)
				MapisodeText(
					text = text,
					color = MapisodeTheme.colorScheme.outlineButtonContent,
					style = MapisodeTheme.typography.bodyMedium,
				)
			}
		} else {
			imageContent()
		}
	}
}
