package com.boostcamp.mapisode.mygroup.viewmodel

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mygroup.R
import com.boostcamp.mapisode.mygroup.intent.GroupCreationIntent
import com.boostcamp.mapisode.mygroup.sideeffect.GroupCreationSideEffect
import com.boostcamp.mapisode.mygroup.state.GroupCreationState
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@HiltViewModel
class GroupCreationViewModel @Inject constructor(
	private val groupRepository: GroupRepository,
	private val userPreferenceDataStore: UserPreferenceDataStore,
) : BaseViewModel<GroupCreationIntent, GroupCreationState, GroupCreationSideEffect>(
	GroupCreationState(),
) {
	private val userId: ConcurrentHashMap<String, String> = ConcurrentHashMap()

	override fun onIntent(intent: GroupCreationIntent) {
		when (intent) {
			is GroupCreationIntent.Initialize -> {
				initializeCreatingGroup()
			}

			is GroupCreationIntent.OnBackClick -> {
				postSideEffect(GroupCreationSideEffect.NavigateToGroupScreen)
			}

			is GroupCreationIntent.OnGroupCreationClick -> {
				checkGroupEdit(intent.title, intent.content, intent.imageUrl)
			}

			is GroupCreationIntent.OnPhotoPickerClick -> {
				intent { copy(isSelectingGroupImage = true) }
			}

			is GroupCreationIntent.OnGroupImageSelect -> {
				imageApply(intent.imageUrl)
			}

			is GroupCreationIntent.OnBackToGroupCreation -> {
				intent { copy(isSelectingGroupImage = false) }
			}
		}
	}

	private fun initializeCreatingGroup() {
		viewModelScope.launch {
			userId["userId"] = userPreferenceDataStore.getUserId().first() ?: ""
			intent {
				copy(
					isInitializing = false,
					group = group.copy(
						id = UUID.randomUUID().toString().replace("-", ""),
						adminUser = userId["userId"] ?: "",
						createdAt = Date(),
						members = persistentListOf(userId["userId"] ?: ""),
					),
				)
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
						GroupCreationSideEffect.ShowToast(R.string.message_error_title_length),
					)
				} else if (content.isEmpty()) {
					postSideEffect(
						GroupCreationSideEffect.ShowToast(R.string.message_error_content_empty),
					)
				} else if (imageUrl.isBlank()) {
					postSideEffect(
						GroupCreationSideEffect.ShowToast(R.string.message_error_image_url_blank),
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
					Timber.e("Group: ${currentState.group}")
					groupRepository.createGroup(currentState.group.toGroupModel())
					postSideEffect(
						GroupCreationSideEffect.ShowToast(
							R.string.message_error_creation_group_success,
						),
					)
					delay(100)
					postSideEffect(GroupCreationSideEffect.NavigateToGroupScreen)
				}
			} catch (e: Exception) {
				postSideEffect(GroupCreationSideEffect.ShowToast(R.string.message_error_edit_group))
				delay(100)
				postSideEffect(GroupCreationSideEffect.Idle)
			}
		}
	}
}
