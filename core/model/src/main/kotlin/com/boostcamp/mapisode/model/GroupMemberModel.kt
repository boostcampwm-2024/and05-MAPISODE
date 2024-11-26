package com.boostcamp.mapisode.model

import java.util.Date

class GroupMemberModel(
	val name: String,
	val email: String,
	val profileUrl: String,
	val joinedAt: Date,
	val groups: List<String>,
)
