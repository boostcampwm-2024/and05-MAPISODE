package com.boostcamp.mapisode.episode.model

import com.boostcamp.mapisode.episode.EpisodeDTO
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

internal fun EpisodeDTO.toFirestoreModel(database: FirebaseFirestore): EpisodeFirestoreModel =
	EpisodeFirestoreModel(
		category = category,
		content = content,
		createdBy = database.collection("user").document(createdBy),
		group = database.collection("group").document(group),
		imageUrls = imageUrls,
		location = location.let { GeoPoint(it.first, it.second) },
		memoryDate = Timestamp(memoryDate),
		tags = tags,
		title = title,
		createdAt = Timestamp(createdAt),
	)
