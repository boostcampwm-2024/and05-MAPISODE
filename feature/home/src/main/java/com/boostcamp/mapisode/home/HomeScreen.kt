package com.boostcamp.mapisode.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.common.util.toEpisodeLatLng
import com.boostcamp.mapisode.designsystem.compose.MapisodeModalBottomSheet
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.AppTypography
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.home.common.ChipType
import com.boostcamp.mapisode.home.common.HomeConstant.DEFAULT_ZOOM
import com.boostcamp.mapisode.home.common.HomeConstant.EXTRA_RANGE
import com.boostcamp.mapisode.home.common.getChipIconTint
import com.boostcamp.mapisode.home.common.mapCategoryToChipType
import com.boostcamp.mapisode.home.component.EpisodeCard
import com.boostcamp.mapisode.home.component.GroupBottomSheetContent
import com.boostcamp.mapisode.home.component.MapisodeChip
import com.boostcamp.mapisode.home.component.MapisodeFabOverlayButton
import com.boostcamp.mapisode.home.component.rememberMarkerImage
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.model.EpisodeModel
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.sample
import timber.log.Timber
import com.boostcamp.mapisode.designsystem.R as Design

@OptIn(FlowPreview::class)
@Composable
internal fun HomeRoute(
	viewModel: HomeViewModel = hiltViewModel(),
	onTextMarkerClick: (EpisodeLatLng) -> Unit = {},
	onEpisodeClick: (String) -> Unit = {},
	onListFabClick: (String) -> Unit = {},
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

	fun loadEpisodesInBounds(
		cameraPositionState: CameraPositionState,
		shouldSort: Boolean = false,
	) {
		val bounds = cameraPositionState.contentBounds

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
					shouldSort = shouldSort,
				),
			)
		}
	}

	LaunchedEffect(
		key1 = cameraPositionState,
		key2 = uiState.selectedChip,
	) {
		snapshotFlow { cameraPositionState.position }
			.distinctUntilChanged()
			.sample(500)
			.collect { _ ->
				if (uiState.isCameraMovingProgrammatically) {
					viewModel.onIntent(HomeIntent.EndProgrammaticCameraMove)
					return@collect
				}
				if (!uiState.isCardVisible) {
					loadEpisodesInBounds(cameraPositionState)
				} else {
					viewModel.onIntent(HomeIntent.MapMovedWhileCardVisible)
				}
			}
	}

	LaunchedEffect(Unit) {
		viewModel.onIntent(HomeIntent.LoadInitialData)
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

				is HomeSideEffect.NavigateToEpisode -> {
					onTextMarkerClick(sideEffect.latLng)
				}

				is HomeSideEffect.NavigateToEpisodeDetail -> {
					onEpisodeClick(sideEffect.episodeId)
				}

				is HomeSideEffect.MoveCameraToPosition -> {
					viewModel.onIntent(HomeIntent.StartProgrammaticCameraMove)
					cameraPositionState.position = CameraPosition(sideEffect.position, DEFAULT_ZOOM)
					loadEpisodesInBounds(cameraPositionState)
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
			viewModel.onIntent(HomeIntent.LoadGroups)
		},
		onListFabClick = { groupId ->
			if (groupId != null) onListFabClick(groupId)
			else Toast.makeText(
				context,
				context.getString(R.string.error_group_load_episodes),
				Toast.LENGTH_SHORT,
			).show()
		},
		onCreateNewEpisode = { latLng ->
			viewModel.onIntent(HomeIntent.ClickTextMarker(latLng.toEpisodeLatLng()))
		},
		onEpisodeMarkerClick = { episode ->
			viewModel.onIntent(HomeIntent.StartProgrammaticCameraMove)
			viewModel.onIntent(HomeIntent.ShowCard(episode))
		},
		onMapClick = {
			if (uiState.isCardVisible) {
				viewModel.onIntent(HomeIntent.CloseCard)
			}
		},
		onRefreshClick = {
			loadEpisodesInBounds(cameraPositionState, shouldSort = true)
		},
		onSwipeStart = {
			viewModel.onIntent(HomeIntent.StartProgrammaticCameraMove)
		},
		onEpisodeClick = { episodeId ->
			viewModel.onIntent(HomeIntent.NavigateToEpisode(episodeId))
		},
		onGroupSelected = { groupId ->
			viewModel.onIntent(HomeIntent.SelectGroup(groupId))
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
	onListFabClick: (String?) -> Unit = {},
	onCreateNewEpisode: (LatLng) -> Unit = {},
	onEpisodeMarkerClick: (EpisodeModel) -> Unit = {},
	onMapClick: () -> Unit = {},
	onRefreshClick: () -> Unit = {},
	onSwipeStart: () -> Unit = {},
	onEpisodeClick: (String) -> Unit = {},
	onGroupSelected: (String) -> Unit = {},
) {
	val context = LocalContext.current
	val eatIcon = remember { OverlayImage.fromResource(Design.drawable.ic_eat_marker_light) }
	val seeIcon = remember { OverlayImage.fromResource(Design.drawable.ic_see_marker_light) }
	val otherIcon = remember { OverlayImage.fromResource(Design.drawable.ic_other_marker_light) }
	val defaultIcon = remember { OverlayImage.fromResource(Design.drawable.ic_other_marker_light) }
	var longClickPosition by rememberSaveable { mutableStateOf<LatLng?>(null) }

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
			onMapLongClick = { _, latLng ->
				longClickPosition = latLng
			},
			onMapClick = { _, _ ->
				longClickPosition = null
				onMapClick()
			},
		) {
			state.episodes.forEach { episode ->
				val chipType = mapCategoryToChipType(episode.category)
				val icon = when (chipType) {
					ChipType.EAT -> eatIcon
					ChipType.SEE -> seeIcon
					ChipType.OTHER -> otherIcon
					else -> defaultIcon
				}

				Marker(
					state = MarkerState(
						position = LatLng(episode.location.latitude, episode.location.longitude),
					),
					icon = icon,
					onClick = {
						longClickPosition = null
						onEpisodeMarkerClick(episode)
						true
					},
				)
			}

			longClickPosition?.let { position ->
				val textMarker = rememberMarkerImage(
					text = "에피소드 생성",
				)

				Marker(
					state = MarkerState(
						position = position,
					),
					icon = textMarker,
					onClick = {
						onCreateNewEpisode(position)
						longClickPosition = null
						true
					},
				)
			}
		}

		Column(
			modifier = Modifier
				.fillMaxSize()
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
			) {
				if (state.showRefreshButton) {
					MapisodeText(
						text = stringResource(R.string.refresh),
						style = AppTypography.bodyLarge,
						modifier = Modifier
							.align(Alignment.Center)
							.clip(RoundedCornerShape(10.dp))
							.clickable { onRefreshClick() }
							.background(
								color = MapisodeTheme.colorScheme.fabBackground,
								shape = RoundedCornerShape(10.dp),
							)
							.padding(vertical = 8.dp, horizontal = 12.dp),
						color = Color.White,
					)
				}

				Column(
					modifier = Modifier
						.align(Alignment.CenterEnd)
						.padding(end = 20.dp),
					verticalArrangement = Arrangement.spacedBy(16.dp),
					horizontalAlignment = Alignment.End,
				) {
					MapisodeFabOverlayButton(
						onClick = onGroupFabClick,
					)

					MapisodeFabOverlayButton(
						onClick = { onListFabClick(state.selectedGroupId) },
						iconId = Design.drawable.ic_list_bulleted,
					)
				}
			}

			if (state.isCardVisible) {
				val pagerState = rememberPagerState(
					initialPage = state.selectedEpisodeIndex,
					initialPageOffsetFraction = 0f,
					pageCount = { state.selectedEpisodes.size },
				)

				LaunchedEffect(pagerState.currentPage) {
					val currentEpisode = state.selectedEpisodes.getOrNull(pagerState.currentPage)
					currentEpisode?.let { episode ->
						val position = LatLng(episode.location.latitude, episode.location.longitude)
						onSwipeStart()
						cameraPositionState.position = CameraPosition(position, DEFAULT_ZOOM)
					}
				}

				LaunchedEffect(state.selectedEpisodes) {
					if (pagerState.currentPage != state.selectedEpisodeIndex) {
						pagerState.scrollToPage(state.selectedEpisodeIndex)
					}
				}

				Spacer(modifier = Modifier.weight(1f))

				HorizontalPager(
					state = pagerState,
					modifier = Modifier
						.fillMaxWidth()
						.align(Alignment.CenterHorizontally),
					verticalAlignment = Alignment.CenterVertically,
					contentPadding = PaddingValues(horizontal = 20.dp),
					pageSpacing = 10.dp,
				) { page ->
					val episode = state.selectedEpisodes[page]

					Box(
						modifier = Modifier.fillMaxWidth(),
						contentAlignment = Alignment.Center,
					) {
						EpisodeCard(
							episode = episode,
							onClick = onEpisodeClick,
						)
					}
				}

				Spacer(modifier = Modifier.height(20.dp))
			}
		}

		MapisodeModalBottomSheet(
			isVisible = state.isBottomSheetVisible,
			onDismiss = onGroupFabClick,
			sheetContent = {
				GroupBottomSheetContent(
					groupList = state.groups,
					onDismiss = onGroupFabClick,
					onGroupClick = onGroupSelected,
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
