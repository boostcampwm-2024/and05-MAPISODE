package com.boostcamp.mapisode.mygroup.intent

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.UiIntent

@Immutable
sealed class GroupEditIntent : UiIntent {
	data class Initialize(val groupId: String) : GroupEditIntent()
	data object OnBackClick : GroupEditIntent()
	data class OnGroupEditClick(val title: String, val content: String, val imageUrl: String) :
		GroupEditIntent()
	data object OnPhotoPickerClick : GroupEditIntent()
	data class OnGroupImageSelect(val imageUrl: String) : GroupEditIntent()
	data object OnBackToGroupCreation : GroupEditIntent()
	data object DenyPhotoPermission : GroupEditIntent()
}
