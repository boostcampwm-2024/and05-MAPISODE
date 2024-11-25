package com.boostcamp.mapisode.home

import com.boostcamp.mapisode.home.common.ChipType
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.naver.maps.geometry.LatLng

sealed class HomeIntent {
	data object RequestLocationPermission : HomeIntent()
	data class SetInitialLocation(val latLng: LatLng) : HomeIntent()
	data class UpdateLocationPermission(val isGranted: Boolean) : HomeIntent() // 위치 권한 설정 여부 업데이트
	data object MarkPermissionRequested : HomeIntent() // 위치 권한 요청 기록
	data class SelectChip(val chipType: ChipType) : HomeIntent()
	data object ShowBottomSheet : HomeIntent()
	data class LoadEpisode(val start: EpisodeLatLng, val end: EpisodeLatLng) : HomeIntent()
	data class ClickTextMarker(val latLng: EpisodeLatLng) : HomeIntent()
}
