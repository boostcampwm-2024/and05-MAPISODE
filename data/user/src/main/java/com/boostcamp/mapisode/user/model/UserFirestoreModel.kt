package com.boostcamp.mapisode.user.model

import com.boostcamp.mapisode.model.GroupModel
import java.util.Date

data class UserFirestoreModel(
	val name: String,
	val email: String,
	val profileUrl: String,
	val joinedAt: Date = Date.from(java.time.Instant.now()),
	val groups: List<GroupModel> = emptyList(),
)
