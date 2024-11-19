package com.boostcamp.mapisode.episode

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.rememberMarkerState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
internal fun EpisodeRoute() {
	val newEpisodeNavController = rememberNavController()
	val markerState = rememberMarkerState()

	NavHost(newEpisodeNavController, startDestination = "new_episode_pics") {
		composable("new_episode_pics") {
			NewEpisodePicsScreen(
				navController = newEpisodeNavController,
			)
		}

		composable("new_episode_info") {
			NewEpisodeInfoScreen(
				navController = newEpisodeNavController,
			)
		}

		composable("pick_location") {
			PickLocationScreen(
				markerState = markerState,
				navController = newEpisodeNavController,
			)
		}

		composable("new_episode_content") {
			NewEpisodeContentScreen(
				navController = newEpisodeNavController,
			)
		}
	}
}
