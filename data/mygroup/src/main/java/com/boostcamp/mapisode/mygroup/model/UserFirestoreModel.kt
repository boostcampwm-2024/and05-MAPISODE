package com.boostcamp.mapisode.mygroup.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class UserFirestoreModel(
	val id: String = "",
	val email: String = "",
	val groups: List<DocumentReference> = listOf(),
	val joinedAt: Timestamp = Timestamp.now(),
	val name: String = "",
	val profileUrl: String = "",
)
