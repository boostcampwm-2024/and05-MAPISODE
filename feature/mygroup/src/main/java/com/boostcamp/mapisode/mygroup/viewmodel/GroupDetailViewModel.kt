package com.boostcamp.mapisode.mygroup.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.model.GroupMemberModel
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mygroup.R
import com.boostcamp.mapisode.mygroup.intent.GroupDetailIntent
import com.boostcamp.mapisode.mygroup.sideeffect.GroupDetailSideEffect
import com.boostcamp.mapisode.mygroup.state.GroupDetailState
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
	private val groupRepository: GroupRepository,
	private val episodeRepository: EpisodeRepository,
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

				is GroupDetailIntent.TryGetUserInfo -> {
					setGroupMembersInfo()
				}

				is GroupDetailIntent.OnEditClick -> {
					postSideEffect(GroupDetailSideEffect.NavigateToGroupEditScreen(groupId.value))
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

				is GroupDetailIntent.OnGroupOutClick -> {
					postSideEffect(GroupDetailSideEffect.WarnGroupOut)
				}

				is GroupDetailIntent.OnGroupOutConfirm -> {
					postSideEffect(GroupDetailSideEffect.RemoveDialog)
					delay(100)
					leaveGroup()
				}

				is GroupDetailIntent.OnGroupOutCancel -> {
					postSideEffect(GroupDetailSideEffect.RemoveDialog)
				}

				is GroupDetailIntent.TryGetGroupEpisodes -> {
					getGroupEpisodes()
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
				if (group.adminUser == userPreferenceDataStore.getUserId().first()) {
					intent {
						copy(
							isGroupOwner = true
						)
					}
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

	private fun setGroupMembersInfo(){
		viewModelScope.launch {
			val group = currentState.group ?: throw Exception()
			val members = group.members
			val memberInfo = mutableListOf<GroupMemberModel>()
			members.forEach { member ->
				val user = groupRepository.getUserInfoByUserId(member)
				memberInfo.add(user)
			}
			intent {
				copy(
					membersInfo = memberInfo
				)
			}
		}
	}

	private fun leaveGroup() {
		viewModelScope.launch {
			val userId = userPreferenceDataStore.getUserId().first() ?: "xtRVRTS7XnBDOYlOezFE"
			val groupId = groupId.value
			try {
				groupRepository.leaveGroup(userId, groupId)
				postSideEffect(GroupDetailSideEffect.ShowToast(R.string.message_group_out_success))
				delay(100)
				postSideEffect(GroupDetailSideEffect.NavigateToGroupScreen)
			} catch (e: Exception) {
				postSideEffect(GroupDetailSideEffect.ShowToast(R.string.message_group_out_fail))
			}
		}
	}

	private fun getGroupEpisodes() {
		viewModelScope.launch {
			try {
				val episodes = episodeRepository.getEpisodesByGroup(groupId.value)
				intent {
					copy(
						episodes = episodes
					)
				}
				Timber.e("episodes: $episodes")
			} catch (e: Exception) {
				postSideEffect(GroupDetailSideEffect.ShowToast(R.string.message_group_not_found))
			}
		}
	}
}