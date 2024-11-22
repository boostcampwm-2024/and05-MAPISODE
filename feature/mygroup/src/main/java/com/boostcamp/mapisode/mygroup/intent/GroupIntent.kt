package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable

@Immutable
sealed class GroupIntent {
	data object LoadGroups : GroupIntent()
}
