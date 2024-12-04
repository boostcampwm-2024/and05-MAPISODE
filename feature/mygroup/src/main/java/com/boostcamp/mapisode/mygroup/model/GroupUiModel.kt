package com.boostcamp.mapisode.mygroup.model

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.GroupModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.util.Date

@Immutable
data class GroupUiModel(
	val id: String,
	val adminUser: String,
	val createdAt: Date,
	val description: String,
	val imageUrl: String,
	val name: String,
	val members: ImmutableList<String>,
) {
	fun toGroupModel(): GroupModel = GroupModel(
		id = id,
		adminUser = adminUser,
		createdAt = createdAt,
		description = description,
		imageUrl = imageUrl,
		name = name,
		members = members.toList(),
	)
}

fun GroupModel.toGroupUiModel(): GroupUiModel = GroupUiModel(
	id = id,
	adminUser = adminUser,
	createdAt = createdAt,
	description = description,
	imageUrl = imageUrl,
	name = name,
	members = members.toImmutableList(),
)
