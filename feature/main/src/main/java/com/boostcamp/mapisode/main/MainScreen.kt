package com.boostcamp.mapisode.main

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.boostcamp.mapisode.designsystem.compose.BottomBar
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.main.component.MainBottomBarItem
import com.boostcamp.mapisode.main.component.MainNavHost
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun MainScreen(
	modifier: Modifier = Modifier,
	navigator: MainNavigator = rememberMainNavigator(),
) {
	MainScreenContent(
		modifier = modifier,
		navigator = navigator,
	)
}

@Composable
internal fun MainScreenContent(
	modifier: Modifier = Modifier,
	navigator: MainNavigator,
) {
	MapisodeScaffold(
		modifier = modifier,
		bottomBar = {
			BottomBar(
				modifier = Modifier.navigationBarsPadding(),
				visible = navigator.shouldShowBottomBar(),
			) {
				MainNavTab.entries.toPersistentList().forEach { tab ->
					MainBottomBarItem(
						tab = tab,
						isSelected = tab == navigator.currentTab,
						onClick = { navigator.navigate(tab) },
					)
				}
			}
		},
	) { paddingValue ->
		MainNavHost(
			navigator = navigator,
			modifier = Modifier.padding(paddingValue),
		)
	}
}
