package com.boostcamp.mapisode.mygroup.sideeffect

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.SideEffect

@Immutable
sealed class GroupDetailSideEffect : SideEffect {
	data object Idle : GroupJoinSideEffect()
	data class ShowToast(val message: String) : GroupDetailSideEffect()
	data object NavigateToGroupEditScreen : GroupDetailSideEffect()
	data object NavigateToGroupScreen : GroupDetailSideEffect()
	data class NavigateToEpisode(val episodeId: String) : GroupDetailSideEffect()
}
