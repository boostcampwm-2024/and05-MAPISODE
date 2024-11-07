package com.boostcamp.mapisode.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import com.boostcamp.mapisode.episode.navigation.addEpisodeNavGraph
import com.boostcamp.mapisode.home.navigation.addHomeNavGraph
import com.boostcamp.mapisode.main.MainNavigator
import com.boostcamp.mapisode.mygroup.navigation.addGroupNavGraph
import com.boostcamp.mapisode.mypage.navigation.addMyPageNavGraph

@Composable
internal fun MainNavHost(
	modifier: Modifier = Modifier,
	navigator: MainNavigator,
) {
	Box(
		modifier = modifier
			.fillMaxSize()
			.background(Color.White),
	) {
		NavHost(
			navController = navigator.navController,
			startDestination = navigator.startDestination,
		) {
			addHomeNavGraph()
			addEpisodeNavGraph()
			addGroupNavGraph()
			addMyPageNavGraph()
		}
	}
}
