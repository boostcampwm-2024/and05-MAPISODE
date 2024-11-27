package com.boostcamp.mapisode.mygroup.viewmodel

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mygroup.R
import com.boostcamp.mapisode.mygroup.intent.GroupEditIntent
import com.boostcamp.mapisode.mygroup.sideeffect.GroupEditSideEffect
import com.boostcamp.mapisode.mygroup.state.GroupEditState
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupEditViewModel @Inject constructor(private val groupRepository: GroupRepository) :
	BaseViewModel<GroupEditIntent, GroupEditState, GroupEditSideEffect>(GroupEditState()) {
	private lateinit var cachedGroupId: String

	override fun onIntent(intent: GroupEditIntent) {
		when (intent) {
			is GroupEditIntent.LoadGroups -> {
				loadGroups(intent.groupId)
			}

			is GroupEditIntent.OnBackClick -> {
				postSideEffect(GroupEditSideEffect.NavigateToGroupDetailScreen)
			}

			is GroupEditIntent.OnGroupEditClick -> {
				checkGroupEdit(intent.title, intent.content, intent.imageUrl)
			}
		}
	}

	private fun loadGroups(groupId: String) {
		viewModelScope.launch {
			cachedGroupId = groupId
			val group = groupRepository.getGroupByGroupId(groupId)
			intent {
				copy(
					isInitializing = false,
					group = group,
				)
			}
		}
	}

	private fun checkGroupEdit(title: String, content: String, imageUrl: String) {
		viewModelScope.launch {
			try {
				if (title.length !in 2..8 || content.length < 10 || imageUrl.isEmpty()) {
					intent { copy(isGroupEditError = true) }
					postSideEffect(GroupEditSideEffect.ShowToast(R.string.message_error_edit_input))
				} else {
					intent { copy(isGroupEditError = false) }
					val editedGroup = currentState.group?.copy(
						name = title,
						description = content,
						imageUrl = imageUrl,
					) ?: return@launch
					groupRepository.updateGroup(cachedGroupId, editedGroup)
					postSideEffect(GroupEditSideEffect.NavigateToGroupDetailScreen)
				}
			} catch (e: Exception) {
				postSideEffect(GroupEditSideEffect.ShowToast(R.string.message_error_edit_group))
			}
		}
	}
}
