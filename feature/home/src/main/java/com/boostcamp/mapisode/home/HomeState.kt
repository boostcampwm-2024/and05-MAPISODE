package com.boostcamp.mapisode.home

import com.boostcamp.mapisode.home.common.HomeConstant.DEFAULT_ZOOM
import com.boostcamp.mapisode.ui.base.UiState
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition

data class HomeState(
	val cameraPosition: CameraPosition = CameraPosition(
		LatLng(
			37.38026976485322,
			127.11537099437301,
		),
		DEFAULT_ZOOM,
	),
	val isInitialLocationSet: Boolean = false,
) : UiState
