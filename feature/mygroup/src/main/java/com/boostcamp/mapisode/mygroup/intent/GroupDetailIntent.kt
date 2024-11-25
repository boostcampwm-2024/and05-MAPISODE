package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable

@Immutable
sealed class GroupDetailIntent {
	data class TryGetGroup(val inviteCode: String) : GroupDetailIntent()
	data object BackToGroupScreen : GroupDetailIntent()
}
