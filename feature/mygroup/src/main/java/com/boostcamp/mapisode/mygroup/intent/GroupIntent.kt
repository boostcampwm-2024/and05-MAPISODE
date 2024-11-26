package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.UiIntent

@Immutable
sealed class GroupIntent : UiIntent {
	data object LoadGroups : GroupIntent()
	data object EndLoadingGroups : GroupIntent()
	data object OnJoinClick : GroupIntent()
	data object OnGroupCreateClick : GroupIntent()
	data class OnGroupDetailClick(val groupId: String) : GroupIntent()
}
