package com.boostcamp.mapisode.episode.model

import com.boostcamp.mapisode.episode.EpisodeDTO
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint

data class EpisodeFirestoreModel(
	val category: String = "",
	val content: String = "",
	val createdBy: DocumentReference? = null,
	val group: DocumentReference? = null,
	val imageUrls: List<String> = emptyList(),
	val location: GeoPoint? = null,
	val memoryDate: Timestamp = Timestamp.now(),
	val tags: List<String> = emptyList(),
	val title: String = "",
	val createdAt: Timestamp = Timestamp.now(),
) {
	fun toDTO(id: String): EpisodeDTO = EpisodeDTO(
		id = id,
		category = category,
		content = content,
		createdBy = createdBy?.id ?: "",
		group = group?.id ?: "",
		imageUrls = imageUrls,
		location = requireNotNull(location?.let { Pair(it.latitude, it.longitude) }),
		memoryDate = memoryDate.toDate(),
		tags = tags,
		title = title,
		createdAt = createdAt.toDate(),
	)
}
