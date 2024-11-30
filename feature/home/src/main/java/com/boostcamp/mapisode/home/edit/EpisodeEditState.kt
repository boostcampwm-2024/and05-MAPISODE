package com.boostcamp.mapisode.home.edit

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import java.util.Date

@Immutable
data class EpisodeEditState(
	val isInitializing: Boolean = true,
	val isSelectingLocation: Boolean = false,
	val isSelectingPicture: Boolean = false,
	val isEditingInProgress: Boolean = false,
	val episode: EpisodeEditInfo = EpisodeEditInfo(),
	val groups: PersistentList<GroupsId> = persistentListOf(),
) : UiState

data class GroupsId(val id: String, val name: String)

data class EpisodeEditInfo(
	val id: String = "",
	val category: String = "",
	val content: String = "",
	val group: String = "",
	val serverImageUrl: PersistentList<String> = persistentListOf(),
	val localImageUrl: PersistentList<Uri> = persistentListOf(),
	val address: String = "",
	val location: EpisodeLatLng = EpisodeLatLng(0.0, 0.0),
	val memoryDate: Date = Date(),
	val tags: List<String> = listOf(),
	val title: String = "",
	val createdBy: String = "",
) {
	fun toDomainModel() = EpisodeModel(
		id = id,
		title = title,
		content = content,
		imageUrls = serverImageUrl,
		imageUrlsUsedForOnlyUpdate = localImageUrl.map { it.toString() },
		address = address,
		location = location,
		group = group,
		category = category,
		tags = tags,
		memoryDate = memoryDate,
		createdBy = createdBy,
	)
}

object CategoryMapper {
	fun mapToCategory(category: String): String = when (category) {
		"먹거리" -> "EAT"
		"볼거리" -> "SEE"
		else -> "OTHER"
	}

	fun mapToCategoryName(category: String): String = when (category) {
		"EAT" -> "먹거리"
		"SEE" -> "볼거리"
		else -> "나머지"
	}
}

fun EpisodeModel.toEpisodeEditInfo(): EpisodeEditInfo = EpisodeEditInfo(
	id = id,
	category = category,
	content = content,
	group = group,
	serverImageUrl = imageUrls.toPersistentList(),
	localImageUrl = imageUrlsUsedForOnlyUpdate.map { Uri.parse(it) }.toPersistentList(),
	address = address,
	location = location,
	memoryDate = memoryDate,
	createdBy = createdBy,
	tags = tags,
	title = title,
)
