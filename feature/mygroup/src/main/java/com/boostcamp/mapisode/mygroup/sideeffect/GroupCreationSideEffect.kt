package com.boostcamp.mapisode.mygroup.sideeffect

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.SideEffect

@Immutable
sealed class GroupCreationSideEffect : SideEffect {
	data object Idle : GroupSideEffect()
	data class ShowToast(val messageResId: Int) : GroupCreationSideEffect()
	data object NavigateToGroupScreen : GroupCreationSideEffect()
}
