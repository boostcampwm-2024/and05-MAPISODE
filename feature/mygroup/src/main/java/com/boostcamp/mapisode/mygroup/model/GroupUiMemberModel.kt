package com.boostcamp.mapisode.mygroup.model

import java.util.Date

data class GroupUiMemberModel(
	val name: String,
	val email: String,
	val profileUrl: String,
	val joinedAt: Date,
	val groups: List<String>,
	val recentCreatedAt: Date?,
	val countEpisode: Int,
)
