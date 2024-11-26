package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.UiIntent

@Immutable
sealed class GroupDetailIntent: UiIntent {
	data class GetGroupId(val groupId: String) : GroupDetailIntent()
	data class TryGetGroup(val inviteCode: String) : GroupDetailIntent()
	data class GoToGroupEditScreen(val groupId: String) : GroupDetailIntent()
	data object BackToGroupScreen : GroupDetailIntent()
}
