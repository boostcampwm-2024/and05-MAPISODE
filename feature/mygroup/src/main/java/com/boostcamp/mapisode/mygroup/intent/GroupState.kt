package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.GroupModel
import com.boostcamp.mapisode.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class GroupState(
	val areGroupsLoading: Boolean = false,
	val groups: ImmutableList<GroupUiModel> = persistentListOf(),
) : UiState

@Immutable
data class GroupUiModel(
	val id: String,
	val adminUser: String,
	val createdAt: String,
	val description: String,
	val imageUrl: String,
	val name: String,
	val members: List<String>,
)

fun GroupModel.toUiModel(): GroupUiModel = GroupUiModel(
	id = id,
	adminUser = adminUser,
	createdAt = createdAt.time.toString(),
	description = description,
	imageUrl = imageUrl,
	name = name,
	members = members.toImmutableList(),
)

fun List<GroupModel>.toUiModels(): List<GroupUiModel> = this.map { it.toUiModel() }
