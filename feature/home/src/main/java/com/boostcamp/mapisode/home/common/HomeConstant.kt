package com.boostcamp.mapisode.home.common

import com.boostcamp.mapisode.model.GroupModel
import kotlinx.collections.immutable.persistentListOf
import java.util.Date

object HomeConstant {
	const val DEFAULT_ZOOM = 16.0
	val tempGroupList = persistentListOf(
		GroupModel(
			id = "1",
			adminUser = "",
			name = "그룹1",
			description = "그룹1 설명",
			imageUrl = "https://avatars.githubusercontent.com/u/98825364?v=4?s=100",
			createdAt = Date(),
			members = emptyList(),
		),
		GroupModel(
			id = "2",
			adminUser = "",
			name = "그룹2",
			description = "그룹2 설명",
			imageUrl = "https://avatars.githubusercontent.com/u/98825364?v=4?s=100",
			createdAt = Date(),
			members = emptyList(),
		),
		GroupModel(
			id = "3",
			adminUser = "",
			name = "그룹3",
			description = "그룹3 설명",
			imageUrl = "https://avatars.githubusercontent.com/u/98825364?v=4?s=100",
			createdAt = Date(),
			members = emptyList(),
		),
	)
	const val EXTRA_RANGE = 0.01 // 약 1 ~ 1.5km
	const val MOCK_IMAGE_URL =
		"https://github.com/user-attachments/assets/411506ef-5fbf-4c6b-b68d-d00343a0b50e"
}
