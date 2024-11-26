package com.boostcamp.mapisode.mygroup.sideeffect

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.SideEffect

@Immutable
sealed class GroupEditSideEffect : SideEffect {
	data object Idle : GroupSideEffect()
	data class ShowToast(val messageResId: Int) : GroupEditSideEffect()
	data object NavigateToGroupDetailScreen : GroupEditSideEffect()
}
