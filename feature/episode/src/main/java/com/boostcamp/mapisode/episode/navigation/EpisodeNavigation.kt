package com.boostcamp.mapisode.episode.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.boostcamp.mapisode.episode.NewEpisodeContentScreen
import com.boostcamp.mapisode.episode.NewEpisodeInfoScreen
import com.boostcamp.mapisode.episode.NewEpisodePicsScreen
import com.boostcamp.mapisode.episode.PickLocationScreen
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.navigation.MainRoute
import com.boostcamp.mapisode.navigation.NewEpisodeRoute

fun NavController.navigateToEpisode(
	navOptions: NavOptions? = null,
) {
	navigate(MainRoute.Episode, navOptions)
}

fun NavController.navigatePickPhoto(
	navOptions: NavOptions? = null,
) {
	navigate(NewEpisodeRoute.PickPhoto, navOptions)
}

fun NavController.navigateWriteInfo(
	navOptions: NavOptions? = null,
) {
	navigate(NewEpisodeRoute.WriteInfo, navOptions)
}

fun NavController.navigatePickLocation(
	navOptions: NavOptions? = null,
) {
	navigate(NewEpisodeRoute.PickLocation, navOptions)
}

fun NavController.navigateWriteContent(
	navOptions: NavOptions? = null,
) {
	navigate(NewEpisodeRoute.WriteContent, navOptions)
}

fun NavGraphBuilder.addEpisodeNavGraph(
	getBackStackEntry: () -> NavBackStackEntry,
	onPopBackToMain: () -> Unit,
	onPopBack: () -> Unit,
	onNavigateToInfo: () -> Unit,
	onClickPickLocation: () -> Unit,
	onNavigateToContent: () -> Unit,
) {
	composable<MainRoute.Episode> { backStackEntry ->
		val lat = backStackEntry.toRoute<MainRoute.Episode>().lat
		val lng = backStackEntry.toRoute<MainRoute.Episode>().lng
		val initialLatLng: EpisodeLatLng? =
			if (lat == null || lng == null) null
			else EpisodeLatLng(lat, lng)

		NewEpisodePicsScreen(
			initialLatLng = initialLatLng,
			viewModel = hiltViewModel(getBackStackEntry()),
			onNavigateToInfo = onNavigateToInfo,
		)
	}

	composable<NewEpisodeRoute.WriteInfo> {
		NewEpisodeInfoScreen(
			viewModel = hiltViewModel(getBackStackEntry()),
			onPopBack = onPopBack,
			onPopBackToMain = onPopBackToMain,
			onClickPickLocation = onClickPickLocation,
			onNavigateToContent = onNavigateToContent,
		)
	}

	composable<NewEpisodeRoute.PickLocation> {
		PickLocationScreen(
			viewModel = hiltViewModel(getBackStackEntry()),
			onPopBackToInfo = onPopBack,
		)
	}

	composable<NewEpisodeRoute.WriteContent> {
		NewEpisodeContentScreen(
			viewModel = hiltViewModel(getBackStackEntry()),
			onPopBack = onPopBack,
			onPopBackToMain = onPopBackToMain,
		)
	}
}
