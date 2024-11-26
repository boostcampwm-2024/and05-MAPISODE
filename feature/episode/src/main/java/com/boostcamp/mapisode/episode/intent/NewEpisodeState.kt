package com.boostcamp.mapisode.episode.intent

import android.net.Uri
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.MAP_DEFAULT_ZOOM
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.ui.base.UiState
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
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
	val episodeContent: NewEpisodeContent = NewEpisodeContent(),
) : UiState {
	fun toDomainModel() = EpisodeModel(
		title = episodeContent.title,
		content = episodeContent.description,
		imageUrls = episodeContent.images.map { it.toString() },
		location = EpisodeLatLng(episodeInfo.location.latitude, episodeInfo.location.longitude),
		group = episodeInfo.group,
		category = episodeInfo.category,
		tags = episodeInfo.tags.split(","),
		memoryDate = episodeInfo.date,
		createdBy = "AndroidDessertClub",
	)
}

data class NewEpisodeInfo(
	val location: LatLng = LatLng(0.0, 0.0),
	val group: String = "",
	val category: String = "",
	val tags: String = "",
	val date: Date = Date(),
)

data class NewEpisodeContent(
	val title: String = "",
	val description: String = "",
	val images: PersistentList<Uri> = emptyList<Uri>().toPersistentList(),
)
