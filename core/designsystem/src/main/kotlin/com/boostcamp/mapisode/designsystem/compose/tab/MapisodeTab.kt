package com.boostcamp.mapisode.designsystem.compose.tab

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import com.boostcamp.mapisode.designsystem.compose.ProvideTextStyle
import com.boostcamp.mapisode.designsystem.compose.ripple.MapisodeRippleBIndication
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeTab(
	selected: Boolean,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	text: @Composable (() -> Unit)? = null,
	icon: @Composable (() -> Unit)? = null,
	interactionSource: MutableInteractionSource? = null,
) {
	val ripple = MapisodeRippleBIndication
	val styledText: @Composable (() -> Unit)? =
		text?.let {
			@Composable {
				ProvideTextStyle(MapisodeTheme.typography.labelLarge, content = text)
			}
		}
	val content: @Composable ColumnScope.() -> Unit = {
		MapisodeTabBaselineLayout(text = styledText, icon = icon)
	}

	Column(
		modifier =
		modifier
			.clip(RectangleShape)
			.selectable(
				selected = selected,
				onClick = onClick,
				enabled = enabled,
				role = Role.Tab,
				interactionSource = interactionSource,
				indication = ripple,
			)
			.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		content = content,
	)
}