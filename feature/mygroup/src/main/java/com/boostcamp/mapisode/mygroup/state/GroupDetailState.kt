package com.boostcamp.mapisode.mygroup.state

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.GroupModel
import com.boostcamp.mapisode.mygroup.model.GroupUiEpisodeModel
import com.boostcamp.mapisode.mygroup.model.GroupUiMemberModel
import com.boostcamp.mapisode.ui.base.UiState

@Immutable
data class GroupDetailState(
	val isGroupIdCaching: Boolean = true,
	val isGroupLoading: Boolean = false,
	val isGroupOwner: Boolean = false,
	val group: GroupModel? = null,
	val membersInfo: List<GroupUiMemberModel> = emptyList(),
	val episodes: List<GroupUiEpisodeModel> = emptyList(),
) : UiState
