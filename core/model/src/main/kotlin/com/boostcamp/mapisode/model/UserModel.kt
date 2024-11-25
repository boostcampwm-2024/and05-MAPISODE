package com.boostcamp.mapisode.model

import java.util.Date

data class UserModel(
	val uid: String,
	val name: String,
	val email: String,
	val profileUrl: String,
	val joinedAt: Date,
	val groups: List<GroupModel>,
)
