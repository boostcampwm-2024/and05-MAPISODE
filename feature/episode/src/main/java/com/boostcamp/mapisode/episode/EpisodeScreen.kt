package com.boostcamp.mapisode.episode

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boostcamp.mapisode.episode.intent.NewEpisodeIntent
import com.boostcamp.mapisode.episode.intent.NewEpisodeViewModel
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.rememberCameraPositionState

@Composable
internal fun EpisodeRoute(
	viewModel: NewEpisodeViewModel = hiltViewModel(),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val newEpisodeNavController = rememberNavController()
	val cameraPositionState: CameraPositionState = rememberCameraPositionState {
		position = uiState.cameraPosition
	}

	NavHost(newEpisodeNavController, startDestination = "new_episode_pics") {
		composable("new_episode_pics") {
			NewEpisodePicsScreen(
				navController = newEpisodeNavController,
				updatePics = { uris ->
					viewModel.onIntent(NewEpisodeIntent.SetEpisodePics(uris))
				},
			)
		}

		composable("new_episode_info") {
			NewEpisodeInfoScreen(
				navController = newEpisodeNavController,
				state = uiState,
				loadMyGroups = {
					viewModel.onIntent(NewEpisodeIntent.LoadMyGroups)
				},
				updateEpisodeInfo = { episodeInfo ->
					viewModel.onIntent(NewEpisodeIntent.SetEpisodeInfo(episodeInfo))
				},
				updateGroup = { group ->
					viewModel.onIntent(NewEpisodeIntent.SetEpisodeGroup(group))
				},
				updateCategory = { category ->
					viewModel.onIntent(NewEpisodeIntent.SetEpisodeCategory(category))
				},
				updateTags = { tags ->
					viewModel.onIntent(NewEpisodeIntent.SetEpisodeTags(tags))
				},
				updateDate = { date ->
					viewModel.onIntent(NewEpisodeIntent.SetEpisodeDate(date))
				},
			)
		}

		composable("pick_location") {
			PickLocationScreen(
				state = uiState,
				cameraPositionState = cameraPositionState,
				navController = newEpisodeNavController,
				updateLocation = { latLng ->
					viewModel.onIntent(NewEpisodeIntent.SetEpisodeLocation(latLng))
				},
				updateAddress = { latLng ->
					viewModel.onIntent(NewEpisodeIntent.SetEpisodeAddress(latLng))
				},
				updateIsCameraMoving = { isCameraMoving ->
					viewModel.onIntent(NewEpisodeIntent.SetIsCameraMoving(isCameraMoving))
				},
			)
		}

		composable("new_episode_content") {
			NewEpisodeContentScreen(
				state = uiState,
				navController = newEpisodeNavController,
				submitEpisode = { episodeContent ->
					viewModel.onIntent(NewEpisodeIntent.SetEpisodeContent(episodeContent))
					viewModel.onIntent(NewEpisodeIntent.CreateNewEpisode)
				},
			)
		}
	}
}
