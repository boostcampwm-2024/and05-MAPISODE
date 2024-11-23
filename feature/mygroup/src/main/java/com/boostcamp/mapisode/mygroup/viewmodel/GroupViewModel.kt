package com.boostcamp.mapisode.mygroup.viewmodel

import com.boostcamp.mapisode.model.GroupItem
import com.boostcamp.mapisode.model.User
import com.boostcamp.mapisode.mygroup.intent.GroupIntent
import com.boostcamp.mapisode.mygroup.intent.GroupSideEffect
import com.boostcamp.mapisode.mygroup.intent.GroupState
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor() :
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
		intent {
			copy(
				areGroupsLoading = true,
				groups = mockItems,
			)
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

val mockItem = GroupItem(
	name = "Group 1",
	imageUrl = "https://avatars.githubusercontent.com/u/127717111?v=4",
	description = "Description for Group 1",
	createdAt = "2021-08-01",
	adminUser = "Admin 1",
	users = listOf(
		User(
			id = "User1",
			displayName = "DisplayName1",
			idToken = "Token1",
		),
	),
)

private val mockItems: List<GroupItem> = List(20) { mockItem }
