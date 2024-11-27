package com.boostcamp.mapisode.mygroup.viewmodel

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mygroup.R
import com.boostcamp.mapisode.mygroup.intent.GroupIntent
import com.boostcamp.mapisode.mygroup.sideeffect.GroupSideEffect
import com.boostcamp.mapisode.mygroup.state.GroupState
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
	private val groupRepository: GroupRepository,
	private val userPreferenceDataStore: UserPreferenceDataStore,
) : BaseViewModel<GroupIntent, GroupState, GroupSideEffect>(GroupState()) {

	override fun onIntent(intent: GroupIntent) {
		when (intent) {
			is GroupIntent.LoadGroups -> {
				loadGroups()
			}

			is GroupIntent.OnJoinClick -> {
				navigateToGroupJoinScreen()
			}

			is GroupIntent.OnGroupCreateClick -> {
				navigateToGroupCreationScreen()
			}

			is GroupIntent.OnGroupDetailClick -> {
				navigateToGroupDetailScreen(intent.groupId)
			}
		}
	}

	private fun loadGroups() {
		viewModelScope.launch {
			try {
				val userId = userPreferenceDataStore.getUserId().first() ?: throw Exception()
				val group = groupRepository
					.getGroupsByUserId(userId)
					.toPersistentList()
				intent {
					copy(
						isInitializing = false,
						groups = group,
					)
				}
			} catch (e: Exception) {
				postSideEffect(GroupSideEffect.ShowToast(R.string.group_load_failure))
			}
		}
	}

	private fun navigateToGroupJoinScreen() {
		viewModelScope.launch {
			postSideEffect(GroupSideEffect.NavigateToGroupJoinScreen)
			delay(100)
			intent { copy(isInitializing = true) }
		}
	}

	private fun navigateToGroupCreationScreen() {
		viewModelScope.launch {
			postSideEffect(GroupSideEffect.NavigateToGroupCreateScreen)
			delay(100)
			intent { copy(isInitializing = true) }
		}
	}

	private fun navigateToGroupDetailScreen(groupId: String) {
		viewModelScope.launch {
			postSideEffect(GroupSideEffect.NavigateToGroupDetailScreen(groupId))
			delay(100)
			intent { copy(isInitializing = true) }
		}
	}
}
