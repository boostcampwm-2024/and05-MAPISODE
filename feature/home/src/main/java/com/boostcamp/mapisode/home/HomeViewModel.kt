package com.boostcamp.mapisode.home

import com.boostcamp.mapisode.home.common.HomeConstant.DEFAULT_ZOOM
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition

class HomeViewModel : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {
	init {
		postSideEffect(HomeSideEffect.SetInitialLocation)
	}

	fun setInitialLocation(latLng: LatLng) {
		intent {
			copy(
				cameraPosition = CameraPosition(latLng, DEFAULT_ZOOM),
				isInitialLocationSet = true,
			)
		}
	}
}
