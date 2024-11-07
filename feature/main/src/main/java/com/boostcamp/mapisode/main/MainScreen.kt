package com.boostcamp.mapisode.main

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.boostcamp.mapisode.main.component.MainBottomBar
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
	Scaffold(
		modifier = modifier,
		bottomBar = {
			MainBottomBar(
				modifier = Modifier.navigationBarsPadding(),
				visible = navigator.shouldShowBottomBar(),
				mainNavTabs = MainNavTab.entries.toPersistentList(),
				currentTab = navigator.currentTab,
				onTabSelected = { navigator.navigate(it) },
			)
		},
	) { paddingValue ->
		MainNavHost(
			navigator = navigator,
			modifier = Modifier.padding(paddingValue),
		)
	}
}
