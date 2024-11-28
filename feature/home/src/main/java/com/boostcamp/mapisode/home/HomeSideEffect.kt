package com.boostcamp.mapisode.home

import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.ui.base.SideEffect
import com.naver.maps.geometry.LatLng

sealed class HomeSideEffect : SideEffect {
	data class ShowToast(val messageResId: Int) : HomeSideEffect()
	data object SetInitialLocation : HomeSideEffect()
	data object RequestLocationPermission : HomeSideEffect()
	data class NavigateToEpisode(val latLng: EpisodeLatLng) : HomeSideEffect()
	data class NavigateToEpisodeDetail(val episodeId: String) : HomeSideEffect()
	data class MoveCameraToPosition(val position: LatLng) : HomeSideEffect()
}
