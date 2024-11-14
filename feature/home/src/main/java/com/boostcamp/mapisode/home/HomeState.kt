package com.boostcamp.mapisode.home

import com.boostcamp.mapisode.home.common.HomeConstant.DEFAULT_ZOOM
import com.boostcamp.mapisode.ui.base.UiState
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition

data class HomeState(
	// 위치 권한 설정을 허용하지 않은 유저에게 보여줄 초기 위치 (양재 코드스쿼드)
	val cameraPosition: CameraPosition = CameraPosition(
		LatLng(
			37.49083317052349,
			127.03343085967185,
		),
		DEFAULT_ZOOM,
	),
	val isInitialLocationSet: Boolean = false,
	val isLocationPermissionGranted: Boolean = false,
	val hasRequestedPermission: Boolean = false,
) : UiState
