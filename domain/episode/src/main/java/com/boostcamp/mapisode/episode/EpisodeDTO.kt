package com.boostcamp.mapisode.episode

import java.util.Date

data class EpisodeDTO(
	val id: String = "",
	val category: String = "",
	val content: String = "",
	val createdBy: String = "",
	val group: String = "",
	val imageUrls: List<String> = emptyList(),
	val location: Pair<Double, Double> = Pair(0.0, 0.0),
	val memoryDate: Date = Date(),
	val tags: List<String> = emptyList(),
	val title: String = "",
	val createdAt: Date = Date(),
)
