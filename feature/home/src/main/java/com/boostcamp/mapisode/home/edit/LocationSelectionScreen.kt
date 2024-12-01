package com.boostcamp.mapisode.home.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.TextAlignment
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.designsystem.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalNaverMapApi::class, FlowPreview::class)
@Composable
internal fun LocationSelectionScreen(
	episodeAddress: String,
	cameraPosition: CameraPosition,
	onSetEpisodeLocation: (LatLng) -> Unit,
	onRequestSelection: (String) -> Unit,
	onDismissSelection: () -> Unit,
) {
	val cameraPositionState: CameraPositionState = rememberCameraPositionState {
		position = cameraPosition
	}
	val episodeMarkerState = rememberMarkerState()
	var isCameraMoving by remember { mutableStateOf(false) }
	var searchingAddress by rememberSaveable { mutableStateOf(episodeAddress) }

	LaunchedEffect(episodeAddress) {
		searchingAddress = episodeAddress
	}

	episodeMarkerState.position = cameraPositionState.position.target

	LaunchedEffect(cameraPositionState.position.target) {
		isCameraMoving = true
		snapshotFlow { cameraPositionState.position.target }
			.debounce(300L)
			.collectLatest { target ->
				onSetEpisodeLocation(target)
				isCameraMoving = false
			}
	}

	MapisodeScaffold(isNavigationBarPaddingExist = true) {
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
					if (isCameraMoving) {
						MapisodeText(
							text = "위치를 탐색중입니다",
							modifier = Modifier.fillMaxWidth(),
							textAlignment = TextAlignment.Center,
							style = MapisodeTheme.typography.headlineSmall,
						)
						MapisodeText(
							text = "탐색중",
							modifier = Modifier.fillMaxWidth(),
							textAlignment = TextAlignment.Center,
							style = MapisodeTheme.typography.bodyLarge,
						)
					} else {
						MapisodeText(
							text = "이 장소로 할래요",
							style = MapisodeTheme.typography.headlineSmall,
						)
						MapisodeText(
							text = searchingAddress,
							style = MapisodeTheme.typography.bodyLarge,
						)
					}

					MapisodeFilledButton(
						modifier = Modifier.fillMaxWidth().height(42.dp),
						onClick = {
							onRequestSelection(searchingAddress)
						},
						text = if (isCameraMoving) "위치 설정중" else "선택하기",
						enabled = isCameraMoving.not(),
						showRipple = true,
					)

					Spacer(modifier = Modifier.height(20.dp))
				}
			}
		}
	}

	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.TopStart,
	){
		MapisodeIconButton(
			onClick = onDismissSelection,
			modifier = Modifier.systemBarsPadding().padding(start = 12.dp),
		) {
			MapisodeIcon(
				id = R.drawable.ic_arrow_back_ios,
			)
		}
	}
}
