package com.boostcamp.mapisode.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.boostcamp.mapisode.home.HomeRoute
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.navigation.MainRoute

fun NavController.navigateHome(
	navOptions: NavOptions? = null,
) {
	navigate(MainRoute.Home, navOptions)
}

fun NavGraphBuilder.addHomeNavGraph(onTextMarkerClick: (EpisodeLatLng) -> Unit) {
	composable<MainRoute.Home> {
		HomeRoute(onTextMarkerClick = onTextMarkerClick)
	}
}
