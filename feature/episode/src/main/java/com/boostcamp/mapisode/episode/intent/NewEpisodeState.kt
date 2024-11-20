package com.boostcamp.mapisode.episode.intent

import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.MAP_DEFAULT_ZOOM
import com.boostcamp.mapisode.ui.base.UiState
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import java.util.Date

data class NewEpisodeState(
	val cameraPosition: CameraPosition = CameraPosition(
		LatLng(
			37.49083317052349,
			127.03343085967185,
		),
		MAP_DEFAULT_ZOOM,
	),
	val episodeInfo: NewEpisodeInfo = NewEpisodeInfo(),
) : UiState

data class NewEpisodeInfo(
	val location: LatLng = LatLng(0.0, 0.0),
	val group: String = "",
	val category: String = "",
	val tags: String = "",
	val date: Date = Date(),
)
