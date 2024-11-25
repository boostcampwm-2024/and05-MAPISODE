package com.boostcamp.mapisode.mygroup.sideeffect

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.SideEffect

@Immutable
sealed class GroupJoinSideEffect : SideEffect {
	data class ShowToast(val messageResId: Int) : GroupJoinSideEffect()
}
