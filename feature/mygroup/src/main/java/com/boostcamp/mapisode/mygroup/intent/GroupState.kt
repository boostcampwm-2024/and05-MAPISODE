package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.GroupItem
import com.boostcamp.mapisode.model.UserModel
import com.boostcamp.mapisode.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Immutable
data class GroupState(
	val areGroupsLoading: Boolean = false,
	val groups: ImmutableList<GroupItemUiModel> = persistentListOf(),
) : UiState

@Immutable
data class GroupItemUiModel(
	val name: String,
	val imageUrl: String,
	val description: String,
	val createdAt: String,
	val adminUser: String,
	val users: ImmutableList<UserModel> = persistentListOf(),
)

fun GroupItem.toUiModel(): GroupItemUiModel = GroupItemUiModel(
	name = name,
	imageUrl = imageUrl,
	description = description,
	createdAt = createdAt,
	adminUser = adminUser,
	users = users.toPersistentList(),
)

fun List<GroupItem>.toUiModels(): List<GroupItemUiModel> = this.map { it.toUiModel() }
