package com.boostcamp.mapisode.mygroup.state

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.GroupModel
import com.boostcamp.mapisode.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class GroupState(
	val isInitializing: Boolean = true,
	val areGroupsLoading: Boolean = false,
	val groups: PersistentList<GroupModel> = persistentListOf(),
) : UiState
