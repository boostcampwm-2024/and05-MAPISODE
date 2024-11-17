package com.boostcamp.mapisode.designsystem.compose.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.Surface
import com.boostcamp.mapisode.designsystem.compose.mapisodeRippleEffect
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeDropdownMenuItem(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	contentPadding: PaddingValues = MapisodeMenuDefaults.DropdownMenuItemContentPadding,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	content: @Composable RowScope.() -> Unit
) {
	Surface(
		modifier = modifier
			.mapisodeRippleEffect(
				rippleColor = Color.Black.copy(alpha = 0.1f),
			)
			.clickable(
				enabled = enabled,
				onClick = onClick,
				interactionSource = interactionSource,
				indication = null,
			)
			.fillMaxWidth(),
		color = if (enabled) {
			MapisodeTheme.colorScheme.surfaceBackground
		} else {
			MapisodeTheme.colorScheme.surfaceBackground.copy(alpha = 0.38f)
		}
	) {
		Row(
			modifier = Modifier.padding(contentPadding),
			verticalAlignment = Alignment.CenterVertically,
			content = content
		)
	}
}

private object MapisodeMenuDefaults {
	val DropdownMenuItemContentPadding = PaddingValues(
		horizontal = 16.dp,
		vertical = 8.dp
	)
}
