package com.boostcamp.mapisode.home

import com.boostcamp.mapisode.home.common.HomeConstant.DEFAULT_ZOOM
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition

class HomeViewModel : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

	fun onIntent(intent: HomeIntent) {
		when (intent) {
			is HomeIntent.RequestLocationPermission -> {
				// 위치 권한 요청이 아직 이루어지지 않은 경우에만 요청
				if (!currentState.hasRequestedPermission) {
					postSideEffect(HomeSideEffect.RequestLocationPermission)
					onIntent(HomeIntent.MarkPermissionRequested)
				}
			}

			is HomeIntent.SetInitialLocation -> {
				setInitialLocation(intent.latLng)
			}

			is HomeIntent.UpdateLocationPermission -> {
				updateLocationPermission(intent.isGranted)
				if (intent.isGranted) {
					postSideEffect(HomeSideEffect.SetInitialLocation)
				} else {
					postSideEffect(HomeSideEffect.ShowToast(R.string.home_location_permission_needed))
				}
			}

			is HomeIntent.MarkPermissionRequested -> {
				setHasRequestedPermission()
			}
		}
	}

	private fun setHasRequestedPermission() {
		intent {
			copy(hasRequestedPermission = true)
		}
	}

	private fun setInitialLocation(latLng: LatLng) {
		intent {
			copy(
				cameraPosition = CameraPosition(latLng, DEFAULT_ZOOM),
				isInitialLocationSet = true,
			)
		}
	}

	private fun updateLocationPermission(isGranted: Boolean) {
		intent {
			copy(isLocationPermissionGranted = isGranted)
		}
	}
}
