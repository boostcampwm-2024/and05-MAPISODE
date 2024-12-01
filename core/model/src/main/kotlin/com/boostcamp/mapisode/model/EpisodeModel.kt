package com.boostcamp.mapisode.model

import java.util.Date

data class EpisodeModel(
	val id: String = "",
	val category: String = "",
	val content: String = "",
	val createdBy: String = "",
	val createdByName: String = "",
	val group: String = "",
	val imageUrls: List<String> = emptyList(),
	val address: String = "",
	val location: EpisodeLatLng = EpisodeLatLng(),
	val memoryDate: Date = Date(),
	val tags: List<String> = emptyList(),
	val title: String = "",
	val createdAt: Date = Date(),
)

data class EpisodeLatLng(val latitude: Double = 0.0, val longitude: Double = 0.0)
