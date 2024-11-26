package com.boostcamp.mapisode.mygroup.state

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.GroupModel
import com.boostcamp.mapisode.ui.base.UiState

@Immutable
data class GroupEditState(
	val isInitializing: Boolean = true,
	val isGroupEditError: Boolean = false,
	val group: GroupModel? = null,
) : UiState
