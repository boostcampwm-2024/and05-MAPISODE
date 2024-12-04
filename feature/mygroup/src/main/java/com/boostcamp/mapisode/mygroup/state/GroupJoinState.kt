package com.boostcamp.mapisode.mygroup.state

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.mygroup.model.GroupCreationModel
import com.boostcamp.mapisode.ui.base.UiState

@Immutable
data class GroupJoinState(
	val isGroupExist: Boolean = false,
	val isGroupLoading: Boolean = false,
	val isJoinedSuccess: Boolean = false,
	val group: GroupCreationModel? = null,
) : UiState
