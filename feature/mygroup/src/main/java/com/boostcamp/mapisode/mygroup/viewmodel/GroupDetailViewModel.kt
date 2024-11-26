package com.boostcamp.mapisode.mygroup.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mygroup.R
import com.boostcamp.mapisode.mygroup.intent.GroupDetailIntent
import com.boostcamp.mapisode.mygroup.sideeffect.GroupDetailSideEffect
import com.boostcamp.mapisode.mygroup.state.GroupDetailState
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
	private val groupRepository: GroupRepository,
	private val userPreferenceDataStore: UserPreferenceDataStore,
) : BaseViewModel<GroupDetailIntent, GroupDetailState, GroupDetailSideEffect>(GroupDetailState()) {
	private val groupId = mutableStateOf("")

	override fun onIntent(intent: GroupDetailIntent) {
		viewModelScope.launch {
			when (intent) {
				is GroupDetailIntent.InitializeGroupDetail -> {
					getGroupDetail(intent.groupId)
				}

				is GroupDetailIntent.TryGetGroup -> {
					tryGetGroup()
				}

				is GroupDetailIntent.OnEditClick -> {
					postSideEffect(GroupDetailSideEffect.NavigateToGroupEditScreen(intent.groupId))
					delay(100)
					intent { copy(isGroupLoading = true) }
				}

				is GroupDetailIntent.OnBackClick -> {
					postSideEffect(GroupDetailSideEffect.NavigateToGroupScreen)
				}

				is GroupDetailIntent.OnEpisodeClick -> {
					postSideEffect(GroupDetailSideEffect.NavigateToEpisode(intent.episodeId))
				}

				is GroupDetailIntent.OnIssueCodeClick -> {
					issueInvitationCode()
				}
			}
		}
	}

	private fun getGroupDetail(groupId: String) {
		this.groupId.value = groupId
		intent {
			copy(
				isGroupIdCaching = false,
				isGroupLoading = true,
			)
		}
	}

	private fun tryGetGroup() {
		viewModelScope.launch {
			try {
				val group = groupRepository.getGroupByGroupId(groupId.value)
				intent {
					copy(
						isGroupLoading = false,
						group = group,
					)
				}
			} catch (e: Exception) {
				postSideEffect(GroupDetailSideEffect.ShowToast(R.string.message_group_not_found))
			}
		}
	}

	private fun issueInvitationCode() {
		viewModelScope.launch {
			try {
				val code = groupRepository.issueInvitationCode(groupId.value)
				postSideEffect(GroupDetailSideEffect.IssueInvitationCode(code))
				delay(100)
				postSideEffect(GroupDetailSideEffect.ShowToast(R.string.message_issue_code_success))
			} catch (e: Exception) {
				postSideEffect(GroupDetailSideEffect.ShowToast(R.string.message_issue_code_fail))
			}
		}
	}
}
