package com.boostcamp.mapisode.model

import java.util.Date

data class GroupModel(
	val id: String,
	val adminUser: String,
	val createdAt: Date,
	val description: String,
	val imageUrl: String,
	val name: String,
	val members: List<String>,
)
