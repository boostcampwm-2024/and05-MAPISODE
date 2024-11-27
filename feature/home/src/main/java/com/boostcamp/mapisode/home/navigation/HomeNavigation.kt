package com.boostcamp.mapisode.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.boostcamp.mapisode.home.HomeRoute
import com.boostcamp.mapisode.home.detail.EpisodeDetailRoute
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.navigation.HomeRoute
import com.boostcamp.mapisode.navigation.MainRoute

fun NavController.navigateHome(
	navOptions: NavOptions? = null,
) {
	navigate(MainRoute.Home, navOptions)
}

fun NavController.navigateEpisodeDetail(
	episodeId: String,
	navOptions: NavOptions? = null,
) {
	navigate(HomeRoute.Detail(episodeId), navOptions)
}

fun NavGraphBuilder.addHomeNavGraph(
	onTextMarkerClick: (EpisodeLatLng) -> Unit,
	onEpisodeClick: (String) -> Unit,
) {
	composable<MainRoute.Home> {
		HomeRoute(
			onTextMarkerClick = onTextMarkerClick,
			onEpisodeClick = onEpisodeClick,
		)
	}

	composable<HomeRoute.Detail> { backStackEntry ->
		val episodeId = backStackEntry.toRoute<String>()
		EpisodeDetailRoute(episodeId = episodeId)
	}
}
