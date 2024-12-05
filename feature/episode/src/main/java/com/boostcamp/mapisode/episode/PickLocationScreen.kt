package com.boostcamp.mapisode.episode

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.episode.intent.NewEpisodeIntent
import com.boostcamp.mapisode.episode.intent.NewEpisodeViewModel
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapType
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.compose.rememberMarkerState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalNaverMapApi::class, FlowPreview::class)
@Composable
internal fun PickLocationScreen(
	viewModel: NewEpisodeViewModel = hiltViewModel(),
	onPopBackToInfo: () -> Unit,
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val cameraPositionState: CameraPositionState = rememberCameraPositionState {
		position = uiState.cameraPosition
	}
	val episodeMarkerState = rememberMarkerState()
	episodeMarkerState.position = cameraPositionState.position.target

	LaunchedEffect(cameraPositionState.position.target) {
		viewModel.onIntent(NewEpisodeIntent.SetIsCameraMoving(true))
		snapshotFlow { cameraPositionState.position.target }
			.debounce(500L)
			.collectLatest { target ->
				viewModel.onIntent(NewEpisodeIntent.SetEpisodeAddress(target))
				viewModel.onIntent(NewEpisodeIntent.SetIsCameraMoving(false))
			}
	}

	MapisodeScaffold(
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = stringResource(R.string.new_episode_menu_title),
				navigationIcon = {
					MapisodeIconButton(
						onClick = {
							onPopBackToInfo()
						},
					) {
						MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_back_ios)
					}
				},
			)
		},
	) {
		Column {
			NaverMap(
				modifier = Modifier.fillMaxHeight(0.75f),
				cameraPositionState = cameraPositionState,
				properties = MapProperties(
					locationTrackingMode = LocationTrackingMode.NoFollow,
					isNightModeEnabled = isSystemInDarkTheme(),
					mapType = MapType.Navi,
				),
				uiSettings = MapUiSettings(
					isZoomControlEnabled = false,
					isLocationButtonEnabled = true,
					isLogoClickEnabled = false,
				),
				locationSource = rememberFusedLocationSource(),
			) {
				Marker(state = episodeMarkerState)
			}
			Box(
				modifier = Modifier.fillMaxHeight(),
				contentAlignment = Alignment.Center,
			) {
				Column(
					modifier = Modifier.padding(20.dp),
					verticalArrangement = Arrangement.spacedBy(20.dp),
				) {
					MapisodeText(
						text = stringResource(R.string.new_episode_pick_location),
						style = MapisodeTheme.typography.headlineSmall,
					)
					MapisodeText(
						text = uiState.episodeAddress,
						style = MapisodeTheme.typography.bodyLarge,
					)
					MapisodeFilledButton(
						modifier = Modifier.fillMaxWidth(),
						onClick = {
							viewModel.onIntent(
								NewEpisodeIntent.SetEpisodeLocation(
									episodeMarkerState.position,
								),
							)
							onPopBackToInfo()
						},
						text = stringResource(R.string.new_episode_pick_location_button),
						enabled = uiState.isCameraMoving.not(),
						showRipple = true,
					)
				}
			}
		}
	}
}
