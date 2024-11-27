package com.boostcamp.mapisode.mygroup.model

import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.model.EpisodeModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint

data class GroupEpisodeFirestoreModel(
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
	fun toDomainModel(id: String): EpisodeModel = EpisodeModel(
		id = id,
		category = category,
		content = content,
		createdBy = createdBy?.id ?: "",
		group = group?.id ?: "",
		imageUrls = imageUrls,
		location = location?.let { EpisodeLatLng(it.latitude, it.longitude) } ?: EpisodeLatLng(),
		memoryDate = memoryDate.toDate(),
		tags = tags,
		title = title,
		createdAt = createdAt.toDate(),
	)
}
