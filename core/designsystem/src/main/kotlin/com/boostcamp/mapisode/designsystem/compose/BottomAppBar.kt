package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun BottomBar(
	modifier: Modifier = Modifier,
	visible: Boolean = true,
	backgroundColor: Color = MapisodeTheme.colorScheme.background,
	contentColor: Color = MapisodeTheme.colorScheme.secondaryText,
	content: @Composable RowScope.() -> Unit,
) {
	Surface(
		color = backgroundColor,
		contentColor = contentColor,
		modifier = modifier
			.fillMaxWidth()
			.height(68.dp),
	) {
		Column {
			MapisodeDivider(thickness = Thickness.Thin)
			AnimatedVisibility(
				visible = visible,
				enter = fadeIn(),
				exit = fadeOut(),
			) {
				Row(
					Modifier
						.selectableGroup(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically,
					content = content,
				)
			}
		}
	}
}
