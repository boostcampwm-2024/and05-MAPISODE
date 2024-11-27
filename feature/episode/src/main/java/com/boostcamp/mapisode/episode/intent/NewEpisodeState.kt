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
	val myGroups: List<GroupInfo> = emptyList(),
	val episodeAddress: String = "",
	val isCameraMoving: Boolean = false,
	val episodeInfo: NewEpisodeInfo = NewEpisodeInfo(),
	val episodeContent: NewEpisodeContent = NewEpisodeContent(),
) : UiState {
	fun toDomainModel(createdBy: String) = EpisodeModel(
		title = episodeContent.title,
		content = episodeContent.description,
		imageUrls = episodeContent.images.map { it.toString() },
		address = episodeAddress,
		location = EpisodeLatLng(episodeInfo.location.latitude, episodeInfo.location.longitude),
		group = requireNotNull(myGroups.find { it.name == episodeInfo.group }?.groupId),
		category = requireNotNull(episodeInfo.category?.name),
		tags = episodeInfo.tags.split(","),
		memoryDate = episodeInfo.date,
		createdBy = createdBy,
	)
}

data class GroupInfo(val name: String, val groupId: String)

enum class EpisodeCategory(val categoryName: String) {
	EAT("먹거리"),
	SEE("볼거리"),
	OTHER("나머지")
}

data class NewEpisodeInfo(
	val location: LatLng = LatLng(0.0, 0.0),
	val group: String = "",
	val category: EpisodeCategory? = null,
	val tags: String = "",
	val date: Date = Date(),
)

data class NewEpisodeContent(
	val title: String = "",
	val description: String = "",
	val images: PersistentList<Uri> = emptyList<Uri>().toPersistentList(),
)
