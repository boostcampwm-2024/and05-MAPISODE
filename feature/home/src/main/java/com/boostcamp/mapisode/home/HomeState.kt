package com.boostcamp.mapisode.home

import com.boostcamp.mapisode.home.common.ChipType
import com.boostcamp.mapisode.home.common.HomeConstant.DEFAULT_ZOOM
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.ui.base.UiState
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class HomeState(
	// 위치 권한 설정을 허용하지 않은 유저에게 보여줄 초기 위치 (양재 코드스쿼드)
	val cameraPosition: CameraPosition = CameraPosition(
		LatLng(
			37.49083317052349,
			127.03343085967185,
		),
		DEFAULT_ZOOM,
	),
	val isInitialLocationSet: Boolean = false, // 사용자의 현재 위치가 초기 설정되었는지 여부
	val isLocationPermissionGranted: Boolean = false, // 위치 권한이 허용되었는지 여부
	val hasRequestedPermission: Boolean = false, // 위치 권한을 요청한 적이 있는지 여부
	val selectedChip: ChipType? = null,
	val isBottomSheetVisible: Boolean = false,
	val episodes: PersistentList<EpisodeModel> = persistentListOf(),
) : UiState
