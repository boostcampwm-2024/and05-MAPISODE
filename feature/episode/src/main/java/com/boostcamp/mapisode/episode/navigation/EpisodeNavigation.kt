package com.boostcamp.mapisode.episode.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.boostcamp.mapisode.episode.EpisodeRoute
import com.boostcamp.mapisode.navigation.MainRoute

fun NavController.navigateEpisode(
	navOptions: NavOptions? = null,
) {
	navigate(MainRoute.Episode, navOptions)
}

fun NavGraphBuilder.addEpisodeNavGraph() {
	composable<MainRoute.Episode> {
		EpisodeRoute()
	}
}
