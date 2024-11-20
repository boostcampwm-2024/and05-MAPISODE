package com.boostcamp.mapisode.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.designsystem.compose.MapisodeModalBottomSheet
import com.boostcamp.mapisode.home.common.ChipType
import com.boostcamp.mapisode.home.common.HomeConstant.DEFAULT_ZOOM
import com.boostcamp.mapisode.home.common.HomeConstant.EXTRA_RANGE
import com.boostcamp.mapisode.home.common.HomeConstant.tempGroupList
import com.boostcamp.mapisode.home.common.getChipIconTint
import com.boostcamp.mapisode.home.common.mapCategoryToChipType
import com.boostcamp.mapisode.home.component.GroupBottomSheetContent
import com.boostcamp.mapisode.home.component.MapisodeChip
import com.boostcamp.mapisode.home.component.MapisodeFabOverlayButton
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.flow.distinctUntilChanged
import timber.log.Timber
import com.boostcamp.mapisode.designsystem.R as Design

@Composable
internal fun HomeRoute(
	viewModel: HomeViewModel = hiltViewModel(),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val context = LocalContext.current
	val fusedLocationClient = remember {
		LocationServices.getFusedLocationProviderClient(context)
	}

	val cameraPositionState = rememberCameraPositionState {
		position = uiState.cameraPosition
	}

	val launcherLocationPermissions = getPermissionsLauncher { isGranted ->
		viewModel.onIntent(HomeIntent.UpdateLocationPermission(isGranted))
	}

	var backPressedTime by remember { mutableLongStateOf(0L) }

	BackHandler(enabled = true) {
		if (System.currentTimeMillis() - backPressedTime <= 2000L) {
			(context as Activity).finish()
		} else {
			Toast.makeText(context, context.getString(R.string.home_exit_alert), Toast.LENGTH_SHORT)
				.show()
		}
		backPressedTime = System.currentTimeMillis()
	}

	LaunchedEffect(cameraPositionState) {
		snapshotFlow { cameraPositionState.contentBounds }
			.distinctUntilChanged()
			.collect { bounds ->
				bounds?.let {
					val extendedStart = EpisodeLatLng(
						it.southWest.latitude - EXTRA_RANGE,
						it.southWest.longitude - EXTRA_RANGE,
					)
					val extendedEnd = EpisodeLatLng(
						it.northEast.latitude + EXTRA_RANGE,
						it.northEast.longitude + EXTRA_RANGE,
					)

					viewModel.onIntent(
						HomeIntent.LoadEpisode(
							start = extendedStart,
							end = extendedEnd,
						),
					)
				}
			}
	}

	LaunchedEffect(viewModel) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is HomeSideEffect.ShowToast -> {
					val message = context.getString(sideEffect.messageResId)
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
				}

				is HomeSideEffect.RequestLocationPermission -> {
					// 위치 권한이 허용되지 않은 경우 권한 요청
					if (!uiState.isLocationPermissionGranted) {
						launcherLocationPermissions.launch(
							arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
						)
					}
				}

				is HomeSideEffect.SetInitialLocation -> {
					// 초기 위치 설정: 권한이 허용된 경우 현재 위치를 가져온다.
					if (ContextCompat.checkSelfPermission(
							context,
							Manifest.permission.ACCESS_FINE_LOCATION,
						) == PackageManager.PERMISSION_GRANTED &&
						!uiState.isInitialLocationSet
					) {
						fusedLocationClient.lastLocation.addOnSuccessListener { location ->
							if (location != null) {
								val userLatLng = LatLng(location.latitude, location.longitude)

								viewModel.onIntent(HomeIntent.SetInitialLocation(userLatLng))
								cameraPositionState.position =
									CameraPosition(userLatLng, DEFAULT_ZOOM)
							} else {
								Timber.e(context.getString(R.string.home_cannot_bring_location_error))
							}
						}
					}
				}
			}
		}
	}

	HomePermissionHandler(
		context = context,
		uiState = uiState,
		launcherLocationPermissions = launcherLocationPermissions,
		updatePermission = { isGranted ->
			viewModel.onIntent(HomeIntent.UpdateLocationPermission(isGranted))
		},
	)

	HomeScreen(
		state = uiState,
		cameraPositionState = cameraPositionState,
		onChipSelected = { chipType ->
			viewModel.onIntent(HomeIntent.SelectChip(chipType))
		},
		onGroupFabClick = {
			viewModel.onIntent(HomeIntent.ShowBottomSheet)
		},
	)
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun HomeScreen(
	state: HomeState,
	cameraPositionState: CameraPositionState,
	onChipSelected: (ChipType) -> Unit = {},
	onGroupFabClick: () -> Unit = {},
) {
	val context = LocalContext.current

	Box(
		modifier = Modifier.fillMaxSize(),
	) {
		NaverMap(
			modifier = Modifier.fillMaxSize(),
			cameraPositionState = cameraPositionState,
			properties = MapProperties(
				locationTrackingMode = LocationTrackingMode.NoFollow,
				isIndoorEnabled = true,
			),
			uiSettings = MapUiSettings(
				isZoomGesturesEnabled = true,
				isZoomControlEnabled = false,
				isLocationButtonEnabled = true,
				isLogoClickEnabled = false,
				isScaleBarEnabled = false,
				isCompassEnabled = false,
			),
			locationSource = rememberFusedLocationSource(),
			onMapClick = { _, _ ->
				// TODO : 마커 찍기 구현
			},
		) {
			state.episodes.forEach { episode ->
				val chipType = mapCategoryToChipType(episode.category)
				val icon = when (chipType) {
					ChipType.EAT -> OverlayImage.fromResource(Design.drawable.ic_eat_marker_light)
					ChipType.SEE -> OverlayImage.fromResource(Design.drawable.ic_see_marker_light)
					ChipType.OTHER -> OverlayImage.fromResource(Design.drawable.ic_other_marker_light)
					else -> OverlayImage.fromResource(Design.drawable.ic_other_marker_light)
				}

				Marker(
					state = MarkerState(
						position = LatLng(episode.location.latitude, episode.location.longitude),
					),
					icon = icon,
					onClick = {
						// TODO : 마커 클릭 시 이벤트 구현
						true
					},
				)
			}
		}

		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 46.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Row(
				horizontalArrangement = Arrangement.spacedBy(23.dp),
				verticalAlignment = Alignment.CenterVertically,
			) {
				ChipType.entries.forEach { chipType ->
					MapisodeChip(
						text = context.getString(chipType.textResId),
						iconId = chipType.iconResId,
						onClick = { onChipSelected(chipType) },
						isSelected = state.selectedChip == chipType,
						iconTint = getChipIconTint(chipType),
					)
				}
			}

			Spacer(modifier = Modifier.height(22.dp))

			Box(
				modifier = Modifier.fillMaxWidth(),
				contentAlignment = Alignment.CenterEnd,
			) {
				MapisodeFabOverlayButton(
					onClick = onGroupFabClick,
					modifier = Modifier.padding(end = 20.dp),
				)
			}
		}

		MapisodeModalBottomSheet(
			isVisible = state.isBottomSheetVisible,
			onDismiss = onGroupFabClick,
			sheetContent = {
				GroupBottomSheetContent(
					myProfileImage = "https://avatars.githubusercontent.com/u/98825364?v=4?s=100",
					groupList = tempGroupList,
					onDismiss = onGroupFabClick,
				)
			},
		)
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
	HomeScreen(
		state = HomeState(),
		cameraPositionState = rememberCameraPositionState {
			position = CameraPosition(
				LatLng(37.38026976485322, 127.11537099437301),
				DEFAULT_ZOOM,
			)
		},
	)
}
