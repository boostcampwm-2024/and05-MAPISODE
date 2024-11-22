package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.GroupItem
import com.boostcamp.mapisode.ui.base.UiState

@Immutable
data class GroupState(
	val areGroupsLoading: Boolean = false,
	val areGroupsVisible: Boolean = false,
	val groups: List<GroupItem> = listOf(),
) : UiState
