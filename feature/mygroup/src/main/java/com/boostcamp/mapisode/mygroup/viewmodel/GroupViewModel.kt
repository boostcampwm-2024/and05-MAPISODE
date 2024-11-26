package com.boostcamp.mapisode.mygroup.viewmodel

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mygroup.R
import com.boostcamp.mapisode.mygroup.intent.GroupIntent
import com.boostcamp.mapisode.mygroup.sideeffect.GroupSideEffect
import com.boostcamp.mapisode.mygroup.state.GroupState
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(private val groupRepository: GroupRepository) :
	BaseViewModel<GroupIntent, GroupState, GroupSideEffect>(GroupState()) {

	override fun onIntent(intent: GroupIntent) {
		when (intent) {
			is GroupIntent.LoadGroups -> {
				loadGroups()
			}

			is GroupIntent.EndLoadingGroups -> {
				confirmGroupsLoaded()
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
				val group = groupRepository
					.getGroupsByUserId("o6UT6Ze1LFgsvekEvj9J")
					.toPersistentList()
				intent {
					copy(
						isInitializing = false,
						areGroupsLoading = true,
						groups = group,
					)
				}
			} catch (e: Exception) {
				postSideEffect(GroupSideEffect.ShowToast(R.string.group_load_failure))
			}
		}
	}

	private fun confirmGroupsLoaded() {
		intent {
			copy(
				areGroupsLoading = false,
			)
		}
	}

	private fun navigateToGroupJoinScreen() {
		postSideEffect(GroupSideEffect.NavigateToGroupJoinScreen)
	}

	private fun navigateToGroupCreationScreen() {
		postSideEffect(GroupSideEffect.NavigateToGroupCreateScreen)
	}

	private fun navigateToGroupDetailScreen(groupId: String) {
		postSideEffect(GroupSideEffect.NavigateToGroupDetailScreen(groupId))
	}
}
