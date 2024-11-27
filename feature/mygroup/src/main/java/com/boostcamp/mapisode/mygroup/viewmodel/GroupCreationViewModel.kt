package com.boostcamp.mapisode.mygroup.viewmodel

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.model.GroupModel
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mygroup.R
import com.boostcamp.mapisode.mygroup.intent.GroupCreationIntent
import com.boostcamp.mapisode.mygroup.sideeffect.GroupCreationSideEffect
import com.boostcamp.mapisode.mygroup.state.GroupCreationState
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GroupCreationViewModel @Inject constructor(
	private val groupRepository: GroupRepository,
	private val userPreferenceDataStore: UserPreferenceDataStore,
) : BaseViewModel<GroupCreationIntent, GroupCreationState, GroupCreationSideEffect>(
	GroupCreationState(),
) {
	override fun onIntent(intent: GroupCreationIntent) {
		when (intent) {
			is GroupCreationIntent.OnBackClick -> {
				postSideEffect(GroupCreationSideEffect.NavigateToGroupScreen)
			}

			is GroupCreationIntent.OnGroupCreationClick -> {
				checkGroupEdit(intent.title, intent.content, intent.imageUrl)
			}
		}
	}

	private fun checkGroupEdit(title: String, content: String, imageUrl: String) {
		viewModelScope.launch {
			try {
				if (title.length !in 2..24 || content.length < 10 || imageUrl.isBlank()) {
					intent { copy(isGroupEditError = true) }
					postSideEffect(
						GroupCreationSideEffect.ShowToast(R.string.message_error_edit_input),
					)
				} else {
					intent { copy(isGroupEditError = false) }
					val newGroupId = UUID.randomUUID().toString().replace("-", "")
					val userId = userPreferenceDataStore.getUserId().first()
					val editedGroup = GroupModel(
						id = newGroupId,
						name = title,
						createdAt = Date(),
						description = content,
						imageUrl = imageUrl,
						adminUser = userId
							?: throw Exception(),
						members = listOf(userId),
					)
					groupRepository.createGroup(editedGroup)
					postSideEffect(
						GroupCreationSideEffect.ShowToast(
							R.string.message_error_creation_group_success,
						),
					)
					delay(100)
					postSideEffect(GroupCreationSideEffect.NavigateToGroupScreen)
				}
			} catch (e: Exception) {
				postSideEffect(
					GroupCreationSideEffect.ShowToast(
						R.string.message_error_creation_group_fail,
					),
				)
			}
		}
	}
}
