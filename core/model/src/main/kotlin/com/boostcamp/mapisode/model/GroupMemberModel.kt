package com.boostcamp.mapisode.model

import java.util.Date

class GroupMemberModel(
	val id: String = "",
	val name: String,
	val email: String,
	val profileUrl: String,
	val joinedAt: Date,
	val groups: List<String>,
)
