package com.boostcamp.mapisode.home

import com.boostcamp.mapisode.home.common.ChipType
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.ui.base.UiIntent
import com.naver.maps.geometry.LatLng

sealed class HomeIntent : UiIntent {
	data object LoadInitialData : HomeIntent()
	data object LoadGroups : HomeIntent()
	data object RequestLocationPermission : HomeIntent()
	data class SetInitialLocation(val latLng: LatLng) : HomeIntent()
	data class UpdateLocationPermission(val isGranted: Boolean) : HomeIntent() // 위치 권한 설정 여부 업데이트
	data object MarkPermissionRequested : HomeIntent() // 위치 권한 요청 기록
	data class SelectChip(val chipType: ChipType) : HomeIntent()
	data object ShowBottomSheet : HomeIntent()
	data class ClickTextMarker(val latLng: EpisodeLatLng) : HomeIntent()
	data class ShowCard(val selectedEpisode: EpisodeModel) : HomeIntent()
	data object CloseCard : HomeIntent()
	data object MapMovedWhileCardVisible : HomeIntent()
	data object StartProgrammaticCameraMove : HomeIntent()
	data object EndProgrammaticCameraMove : HomeIntent()
	data class NavigateToEpisode(val episodeId: String) : HomeIntent()
	data class SelectGroup(val groupId: String) : HomeIntent()
	data class LoadEpisode(
		val start: EpisodeLatLng,
		val end: EpisodeLatLng,
		val shouldSort: Boolean = false,
	) : HomeIntent()
}
