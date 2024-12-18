package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
	backgroundColor: Color = MapisodeTheme.colorScheme.navBackground,
	contentColor: Color = MapisodeTheme.colorScheme.navUnselectedItem,
	content: @Composable RowScope.() -> Unit,
) {
	AnimatedVisibility(
		visible = visible,
		enter = expandVertically(),
		exit = shrinkVertically(),
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
