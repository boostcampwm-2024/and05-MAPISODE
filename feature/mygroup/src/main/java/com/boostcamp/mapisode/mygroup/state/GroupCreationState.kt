package com.boostcamp.mapisode.mygroup.state

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.UiState

@Immutable
data class GroupCreationState(
	val isGroupEditError: Boolean = false,
) : UiState
