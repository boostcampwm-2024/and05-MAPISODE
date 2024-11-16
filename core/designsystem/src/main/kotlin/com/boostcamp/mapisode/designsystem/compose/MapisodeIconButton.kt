package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import com.boostcamp.mapisode.designsystem.theme.LocalMapisodeIconColor
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeIconButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	backgroundColor: Color = MapisodeTheme.colorScheme.iconButtonBackground,
	contentColor: Color? = null,
	interactionSource: MutableInteractionSource? = null,
	content: @Composable () -> Unit,
) {
	Box(
		modifier = modifier
			.size(IconSize.Large.value)
			.clip(
				shape = RoundedCornerShape(100),
			)
			.background(color = backgroundColor)
			.mapisodeRippleEffect(
				enabled = enabled,
				rippleColor = MapisodeTheme.colorScheme.iconButtonDisabled.copy(alpha = 0.1f),
			)
			.clickable(
				onClick = onClick,
				enabled = enabled,
				role = Role.Button,
				interactionSource = interactionSource,
				indication = null,
			),
		contentAlignment = Alignment.Center,
	) {
		val finalContentColor = contentColor?.copy(alpha = if (enabled) 1f else 0.5f)
			?: MapisodeTheme.colorScheme.run {
				if (enabled) iconButtonEnabled else iconButtonDisabled
			}
		CompositionLocalProvider(
			value = LocalMapisodeIconColor provides finalContentColor,
			content = content,
		)
	}
}

