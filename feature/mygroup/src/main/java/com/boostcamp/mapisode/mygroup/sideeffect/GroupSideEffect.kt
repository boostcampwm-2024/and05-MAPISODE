package com.boostcamp.mapisode.mygroup.sideeffect

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.SideEffect

@Immutable
sealed class GroupSideEffect : SideEffect {
	data object Idle : GroupSideEffect()
	data class ShowToast(val messageResId: Int) : GroupSideEffect()
	data object NavigateToGroupJoinScreen : GroupSideEffect()
	data object NavigateToGroupCreateScreen : GroupSideEffect()
	data class NavigateToGroupDetailScreen(val groupId: String) : GroupSideEffect()
}
