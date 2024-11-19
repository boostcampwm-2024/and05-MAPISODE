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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import com.boostcamp.mapisode.designsystem.compose.ProvideTextStyle
import com.boostcamp.mapisode.designsystem.compose.ripple.MapisodeRippleBIndication
import com.boostcamp.mapisode.designsystem.theme.LocalMapisodeContentColor
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeTab(
	selected: Boolean,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	text: @Composable (() -> Unit)? = null,
	icon: @Composable (() -> Unit)? = null,
	selectedContentColor: Color = LocalMapisodeContentColor.current,
	unselectedContentColor: Color = selectedContentColor,
	interactionSource: MutableInteractionSource? = null
) {
	// 리플 색상은 탭 선택 배경색과 동일해야 함
	val ripple = MapisodeRippleBIndication

	val styledText: @Composable (() -> Unit)? =
		text?.let {
			@Composable {
				ProvideTextStyle(MapisodeTheme.typography.labelMedium, content = text)
			}
		}

	val content: @Composable ColumnScope.() -> Unit = {
		MapisodeTabBaselineLayout(text = styledText, icon = icon)
	}

	MapisodeTabTransition(selectedContentColor, unselectedContentColor, selected) {
		Column(
			modifier =
			modifier
				.selectable(
					selected = selected,
					onClick = onClick,
					enabled = enabled,
					role = Role.Tab,
					interactionSource = interactionSource,
					indication = ripple
				)
				.fillMaxWidth(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
			content = content
		)
	}
}
