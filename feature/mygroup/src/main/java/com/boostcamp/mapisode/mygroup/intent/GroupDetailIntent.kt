package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.UiIntent

@Immutable
sealed class GroupDetailIntent : UiIntent {
	data class InitializeGroupDetail(val groupId: String) : GroupDetailIntent()
	data class TryGetGroup(val inviteCode: String) : GroupDetailIntent()
	data object TryGetUserInfo : GroupDetailIntent()
	data class OnEditClick(val groupId: String) : GroupDetailIntent()
	data object OnBackClick : GroupDetailIntent()
	data class OnEpisodeClick(val episodeId: String) : GroupDetailIntent()
	data object OnIssueCodeClick : GroupDetailIntent()
}
