package com.boostcamp.mapisode.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.home.common.HomeConstant.DEFAULT_ZOOM
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import timber.log.Timber

@Composable
internal fun HomeRoute(
	viewModel: HomeViewModel = hiltViewModel(),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val snackbarHostState = remember { SnackbarHostState() }
	val context = LocalContext.current
	val focusLocationProviderClient = remember {
		LocationServices.getFusedLocationProviderClient(context)
	}

	val cameraPositionState = rememberCameraPositionState {
		position = uiState.cameraPosition
	}

	LaunchedEffect(viewModel) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is HomeSideEffect.ShowToast -> {
					snackbarHostState.showSnackbar(sideEffect.message)
				}

				is HomeSideEffect.SetInitialLocation -> {
					if (!uiState.isInitialLocationSet) {
						// 현재 위치 가져오기
						if (ActivityCompat.checkSelfPermission(
								context,
								Manifest.permission.ACCESS_FINE_LOCATION,
							) != PackageManager.PERMISSION_GRANTED &&
							ActivityCompat.checkSelfPermission(
								context,
								Manifest.permission.ACCESS_COARSE_LOCATION,
							) != PackageManager.PERMISSION_GRANTED
						) {
							// 권한이 없을 경우 요청 필요
							snackbarHostState.showSnackbar("위치 권한이 필요합니다.")
							return@collect
						}
						focusLocationProviderClient.lastLocation.addOnSuccessListener { location ->
							if (location != null) {
								val userLatLng = LatLng(location.latitude, location.longitude)
								// ViewModel의 상태 업데이트
								viewModel.setInitialLocation(userLatLng)
								// 카메라 위치 업데이트
								cameraPositionState.position =
									CameraPosition(userLatLng, DEFAULT_ZOOM)
							} else {
								Timber.e("위치 정보를 가져올 수 없습니다.")
							}
						}
					}
				}
			}
		}
	}

	HomeScreen(
		state = uiState,
		cameraPositionState = cameraPositionState,
		snackbarHostState = snackbarHostState,
	)
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun HomeScreen(
	state: HomeState,
	cameraPositionState: CameraPositionState,
	snackbarHostState: SnackbarHostState,
) {
	Scaffold(
		snackbarHost = { SnackbarHost(snackbarHostState) },
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(it),
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
			)
		}
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
		snackbarHostState = SnackbarHostState(),
	)
}
