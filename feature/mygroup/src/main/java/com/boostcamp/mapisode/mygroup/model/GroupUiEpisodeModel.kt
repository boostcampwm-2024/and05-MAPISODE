package com.boostcamp.mapisode.mygroup.model

import com.boostcamp.mapisode.model.EpisodeModel
import java.util.Date

data class GroupUiEpisodeModel(
	val title: String = "",
	val imageUrls: List<String> = emptyList(),
	val category: String = "",
	val createdBy: String = "",
	val content: String = "",
	val address: String = "",
	val memoryDate: Date = Date(),
	val createdAt: Date = Date(),
) {
	fun toEpisodeModel(): EpisodeModel {
		return EpisodeModel(
			title = title,
			imageUrls = imageUrls,
			category = category,
			createdBy = createdBy,
			content = content,
			address = address,
			memoryDate = memoryDate,
			createdAt = createdAt,
		)
	}
}

fun EpisodeModel.toGroupUiEpisodeModel(writer: String): GroupUiEpisodeModel {
	return GroupUiEpisodeModel(
		title = title,
		imageUrls = imageUrls,
		category = category,
		createdBy = writer,
		content = content,
		address = address,
		memoryDate = memoryDate,
		createdAt = createdAt,
	)
}
