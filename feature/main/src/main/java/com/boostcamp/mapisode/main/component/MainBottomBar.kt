package com.boostcamp.mapisode.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.main.MainNavTab
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun MainBottomBar(
	modifier: Modifier = Modifier,
	visible: Boolean,
	mainNavTabs: List<MainNavTab>,
	currentTab: MainNavTab?,
	onTabSelected: (MainNavTab) -> Unit,
) {
	AnimatedVisibility(
		visible = visible,
		enter = fadeIn(),
		exit = fadeOut(),
	) {
		Row(
			modifier = modifier
				.fillMaxWidth()
				.height(64.dp)
				.border(
					width = 1.dp,
					color = Color(0xFF56524E),
				)
				.background(color = Color.White),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
		) {
			mainNavTabs.forEach { tab ->
				MainBottomBarItem(
					tab = tab,
					isSelected = tab == currentTab,
					onClick = { onTabSelected(tab) },
				)
			}
		}
	}
}

@Preview
@Composable
private fun MainBottomBarPreview() {
	MaterialTheme {
		MainBottomBar(
			visible = true,
			mainNavTabs = MainNavTab.entries.toPersistentList(),
			currentTab = MainNavTab.HOME,
			onTabSelected = { },
		)
	}
}
