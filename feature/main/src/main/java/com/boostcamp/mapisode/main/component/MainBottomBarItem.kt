package com.boostcamp.mapisode.main.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.main.MainNavTab

@Composable
internal fun RowScope.MainBottomBarItem(
	modifier: Modifier = Modifier,
	tab: MainNavTab,
	isSelected: Boolean,
	onClick: () -> Unit,
) {
	Column(
		modifier = modifier
			.weight(1f)
			.fillMaxHeight()
			.selectable(
				selected = isSelected,
				indication = null,
				role = null,
				interactionSource = remember { MutableInteractionSource() },
				onClick = onClick,
			),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		Spacer(modifier = Modifier.height(8.dp))

		Icon(
			painter = painterResource(tab.iconResId),
			contentDescription = tab.contentDescription,
			tint = if (isSelected) {
				Color(0xFFF0BE6D)
			} else {
				MaterialTheme.colorScheme.outline
			},
		)

		Text(
			text = tab.contentDescription,
			color = if (isSelected) Color(0xFFF0BE6D) else Color(0xFF56524E),
		)

		Spacer(modifier = Modifier.height(8.dp))
	}
}
