package com.boostcamp.mapisode.designsystem.compose.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.ProvideTextStyle
import com.boostcamp.mapisode.designsystem.compose.Surface
import com.boostcamp.mapisode.designsystem.compose.Thickness
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
internal fun MapisodeButton(
	onClick: () -> Unit,
	backgroundColors: Color,
	contentColor: Color,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	showBorder: Boolean = false,
	borderColor: Color = Color.Transparent,
	rounding: Dp = 8.dp,
	contentPadding: PaddingValues = MapisodeButtonDefaults.ContentPadding,
	showRipple: Boolean = false,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	content: @Composable RowScope.() -> Unit,
) {
	Surface(
		onClick = onClick,
		modifier = modifier,
		enabled = enabled,
		rounding = rounding,
		color = backgroundColors,
		contentColor = contentColor,
		border = if (showBorder) BorderStroke(Thickness.Thin.value, borderColor) else null,
		showRipple = showRipple,
		interactionSource = interactionSource,
	) {
		ProvideTextStyle(value = MapisodeTheme.typography.labelLarge) {
			Row(
				Modifier
					.defaultMinSize(
						minWidth = MapisodeButtonDefaults.MinWidth,
						minHeight = MapisodeButtonDefaults.MinHeight,
					)
					.padding(contentPadding),
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically,
				content = content,
			)
		}
	}
}

object MapisodeButtonDefaults {
	private val ButtonHorizontalPadding = 16.dp
	private val ButtonVerticalPadding = 15.dp

	val ContentPadding = PaddingValues(
		horizontal = ButtonHorizontalPadding,
		vertical = ButtonVerticalPadding,
	)

	val MinWidth = 64.dp
	val MinHeight = 36.dp
}
