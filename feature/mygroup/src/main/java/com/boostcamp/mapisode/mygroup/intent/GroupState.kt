package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.GroupModel
import com.boostcamp.mapisode.ui.base.UiState

@Immutable
data class GroupState(
	val areGroupsLoading: Boolean,
	val areGroupsVisible: Boolean,
	val groups: List<GroupModel>,
) : UiState
