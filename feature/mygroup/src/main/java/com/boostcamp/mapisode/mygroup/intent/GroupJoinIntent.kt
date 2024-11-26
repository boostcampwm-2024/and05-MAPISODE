package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.UiIntent

@Immutable
sealed class GroupJoinIntent : UiIntent {
	data class TryGetGroup(val inviteCode: String) : GroupJoinIntent()
	data object OnJoinClick : GroupJoinIntent()
	data object OnBackClick : GroupJoinIntent()
}
