package com.boostcamp.mapisode.mygroup.state

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.mygroup.model.GroupUiEpisodeModel
import com.boostcamp.mapisode.mygroup.model.GroupUiMemberModel
import com.boostcamp.mapisode.mygroup.model.GroupUiModel
import com.boostcamp.mapisode.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class GroupDetailState(
	val isGroupIdCaching: Boolean = true,
	val isGroupLoading: Boolean = false,
	val isGroupOwner: Boolean = false,
	val group: GroupUiModel? = null,
	val membersInfo: ImmutableList<GroupUiMemberModel> = persistentListOf(),
	val episodes: ImmutableList<GroupUiEpisodeModel> = persistentListOf(),
) : UiState
