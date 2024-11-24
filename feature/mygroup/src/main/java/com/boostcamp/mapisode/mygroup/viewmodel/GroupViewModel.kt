package com.boostcamp.mapisode.mygroup.viewmodel

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mygroup.intent.GroupIntent
import com.boostcamp.mapisode.mygroup.intent.GroupSideEffect
import com.boostcamp.mapisode.mygroup.intent.GroupState
import com.boostcamp.mapisode.mygroup.intent.toUiModel
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(private val groupRepository: GroupRepository) :
	BaseViewModel<GroupState, GroupSideEffect>(GroupState()) {

	fun onIntent(intent: GroupIntent) {
		when (intent) {
			is GroupIntent.LoadGroups -> {
				loadGroups()
			}

			is GroupIntent.EndLoadingGroups -> {
				confirmGroupsLoaded()
			}
		}
	}

	private fun loadGroups() {
		viewModelScope.launch {
			try {
				val group = groupRepository.getAllGroups().map { it.toUiModel() }.toImmutableList()
				intent {
					copy(
						areGroupsLoading = true,
						groups = group,
					)
				}
			} catch (e: Exception) {
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
}
