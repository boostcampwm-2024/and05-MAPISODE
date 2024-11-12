package com.boostcamp.mapisode.main.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
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
		val selectedColor = MapisodeTheme.colorScheme.accentSelected
		val unselectedColor = MapisodeTheme.colorScheme.secondaryText

		Spacer(modifier = Modifier.height(8.dp))

		MapisodeIcon(
			id = tab.iconResId,
			contentDescription = tab.contentDescription,
			tint = if (isSelected) selectedColor else unselectedColor,
		)

		MapisodeText(
			text = tab.contentDescription,
			color = if (isSelected) selectedColor else unselectedColor,
		)

		Spacer(modifier = Modifier.height(8.dp))
	}
}
