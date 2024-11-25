package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable

@Immutable
sealed class GroupJoinIntent {
	data class TryGetGroup(val inviteCode: String) : GroupJoinIntent()
	data class JoinTheGroup(val userId: String, val groupId: String) : GroupJoinIntent()
	data object BackToGroupScreen : GroupJoinIntent()
}
