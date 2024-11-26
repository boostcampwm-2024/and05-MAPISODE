package com.boostcamp.mapisode.mygroup.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mygroup.intent.GroupDetailIntent
import com.boostcamp.mapisode.mygroup.intent.GroupSideEffect
import com.boostcamp.mapisode.mygroup.state.GroupDetailState
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
	private val groupRepository: GroupRepository,
	private val userPreferenceDataStore: UserPreferenceDataStore,
	private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<GroupDetailIntent, GroupDetailState, GroupSideEffect>(GroupDetailState()) {

	fun getGroupDetail(groupId: String) {

	}

	override fun onIntent(intent: GroupDetailIntent) {
		TODO("Not yet implemented")
	}
}
