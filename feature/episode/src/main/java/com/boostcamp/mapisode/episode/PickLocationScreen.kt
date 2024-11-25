package com.boostcamp.mapisode.episode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.compose.rememberMarkerState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalNaverMapApi::class, FlowPreview::class)
@Composable
internal fun PickLocationScreen(
	cameraPositionState: CameraPositionState,
	navController: NavController,
	updateLocation: (LatLng) -> Unit,
) {
	val episodeMarkerState = rememberMarkerState()
	episodeMarkerState.position = cameraPositionState.position.target
	val locationStateFlow = remember {
		MutableStateFlow(cameraPositionState.position.target)
	}
	val location by locationStateFlow.collectAsStateWithLifecycle()

	LaunchedEffect(cameraPositionState.position.target) {
		locationStateFlow
			.debounce(500L)
			.collectLatest {
				locationStateFlow.value = cameraPositionState.position.target
			}
	}

	MapisodeScaffold(
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = stringResource(R.string.new_episode_menu_title),
				navigationIcon = {
					MapisodeIconButton(onClick = { navController.navigateUp() }) {
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
					locationTrackingMode = LocationTrackingMode.Follow,
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
						text = latLngString(location),
						style = MapisodeTheme.typography.bodyLarge,
					)
					MapisodeFilledButton(
						modifier = Modifier.fillMaxWidth(),
						text = stringResource(R.string.new_episode_pick_location_button),
						onClick = {
							updateLocation(episodeMarkerState.position)
							navController.popBackStack("new_episode_info", false)
						},
						showRipple = true,
					)
				}
			}
		}
	}
}
