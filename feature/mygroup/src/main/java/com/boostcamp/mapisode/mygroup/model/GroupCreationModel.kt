package com.boostcamp.mapisode.mygroup.model

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.GroupModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import java.util.Date

@Immutable
data class GroupCreationModel(
	val id: String = "",
	val adminUser: String = "",
	val createdAt: Date = Date(),
	val description: String = "",
	val imageUrl: String = "",
	val name: String = "",
	val members: PersistentList<String> = persistentListOf(),
) {
	fun toGroupModel(): GroupModel = GroupModel(
		id = id,
		adminUser = adminUser,
		createdAt = createdAt,
		description = description,
		imageUrl = imageUrl,
		name = name,
		members = members,
	)
}

fun GroupModel.toGroupCreationModel(): GroupCreationModel = GroupCreationModel(
	id = id,
	adminUser = adminUser,
	createdAt = createdAt,
	description = description,
	imageUrl = imageUrl,
	name = name,
	members = members.toPersistentList(),
)
