package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.SideEffect

@Immutable
sealed class GroupSideEffect : SideEffect {
	data class ShowToast(val messageResId: Int) : GroupSideEffect()
}
