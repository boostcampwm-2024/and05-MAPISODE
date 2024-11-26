package com.boostcamp.mapisode.mygroup.sideeffect

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.SideEffect

@Immutable
sealed class GroupDetailSideEffect : SideEffect {
	data object Idle : GroupJoinSideEffect()
	data class ShowToast(val messageResId: Int) : GroupDetailSideEffect()
	data class NavigateToGroupEditScreen(val groupId: String) : GroupDetailSideEffect()
	data object NavigateToGroupScreen : GroupDetailSideEffect()
	data class NavigateToEpisode(val episodeId: String) : GroupDetailSideEffect()
	data class IssueInvitationCode(val invitationCode: String) : GroupDetailSideEffect()
	data object WarnGroupOut : GroupDetailSideEffect()
	data object RemoveDialog : GroupDetailSideEffect()
}
