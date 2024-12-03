package com.boostcamp.mapisode.mygroup.viewmodel

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mygroup.R
import com.boostcamp.mapisode.mygroup.intent.GroupEditIntent
import com.boostcamp.mapisode.mygroup.model.toGroupCreationModel
import com.boostcamp.mapisode.mygroup.sideeffect.GroupEditSideEffect
import com.boostcamp.mapisode.mygroup.state.GroupEditState
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupEditViewModel @Inject constructor(private val groupRepository: GroupRepository) :
	BaseViewModel<GroupEditIntent, GroupEditState, GroupEditSideEffect>(GroupEditState()) {

	override fun onIntent(intent: GroupEditIntent) {
		when (intent) {
			is GroupEditIntent.Initialize -> {
				initializeCreatingGroup(intent.groupId)
			}

			is GroupEditIntent.OnBackClick -> {
				postSideEffect(GroupEditSideEffect.NavigateToGroupDetailScreen)
			}

			is GroupEditIntent.OnGroupEditClick -> {
				checkGroupEdit(intent.title, intent.content, intent.imageUrl)
			}

			GroupEditIntent.DenyPhotoPermission -> postSideEffect(
				GroupEditSideEffect.ShowToast(R.string.message_error_permission_denied),
			)

			is GroupEditIntent.OnGroupImageSelect -> {
				imageApply(intent.imageUrl)
			}

			GroupEditIntent.OnPhotoPickerClick -> {
				intent { copy(isSelectingGroupImage = true) }
			}

			GroupEditIntent.OnBackToGroupCreation -> intent { copy(isSelectingGroupImage = false) }
		}
	}

	private fun initializeCreatingGroup(groupId: String) {
		viewModelScope.launch {
			try {
				val group = groupRepository.getGroupByGroupId(groupId).toGroupCreationModel()
				intent {
					copy(
						isInitializing = true,
						group = group,
					)
				}
			} catch (e: Exception) {
				postSideEffect(GroupEditSideEffect.ShowToast(R.string.group_load_failure))
			}
		}
	}

	private fun imageApply(imageUrl: String) {
		viewModelScope.launch {
			intent {
				copy(
					isSelectingGroupImage = false,
					group = group.copy(imageUrl = imageUrl),
				)
			}
		}
	}

	private fun checkGroupEdit(title: String, content: String, imageUrl: String) {
		viewModelScope.launch {
			try {
				if (title.length !in 2..24) {
					postSideEffect(
						GroupEditSideEffect.ShowToast(R.string.message_error_title_length),
					)
				} else if (content.isEmpty()) {
					postSideEffect(
						GroupEditSideEffect.ShowToast(R.string.message_error_content_empty),
					)
				} else if (imageUrl.isBlank()) {
					postSideEffect(
						GroupEditSideEffect.ShowToast(R.string.message_error_image_url_blank),
					)
				} else {
					intent {
						copy(
							group = group.copy(
								name = title,
								description = content,
								imageUrl = imageUrl,
							),
						)
					}
					groupRepository.updateGroup(currentState.group.toGroupModel())
					postSideEffect(
						GroupEditSideEffect.ShowToast(R.string.message_success_edit_group),
					)
					delay(100)
					postSideEffect(GroupEditSideEffect.NavigateToGroupDetailScreen)
				}
			} catch (e: Exception) {
				postSideEffect(GroupEditSideEffect.ShowToast(R.string.message_error_edit_group))
				delay(100)
				postSideEffect(GroupEditSideEffect.Idle)
			}
		}
	}
}
