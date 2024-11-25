package com.boostcamp.mapisode.episode.model

import com.boostcamp.mapisode.model.EpisodeModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

internal fun EpisodeModel.toFirestoreModel(
	database: FirebaseFirestore,
	imageUrls: List<String>,
): EpisodeFirestoreModel =
	EpisodeFirestoreModel(
		category = category,
		content = content,
		createdBy = database.collection("user").document(createdBy),
		group = database.collection("group").document(group),
		imageUrls = imageUrls,
		location = GeoPoint(location.latitude, location.longitude),
		memoryDate = Timestamp(memoryDate),
		tags = tags,
		title = title,
		createdAt = Timestamp(createdAt),
	)
