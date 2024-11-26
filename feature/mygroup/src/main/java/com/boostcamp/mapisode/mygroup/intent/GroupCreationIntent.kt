package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.UiIntent

@Immutable
sealed class GroupCreationIntent : UiIntent {
	data object OnBackClick : GroupCreationIntent()
	data class OnGroupCreationClick(
		val title: String,
		val content: String,
		val imageUrl: String,
	) : GroupCreationIntent()
}
