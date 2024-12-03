package com.boostcamp.mapisode.mypage.viewmodel

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mypage.R
import com.boostcamp.mapisode.mypage.intent.ProfileEditIntent
import com.boostcamp.mapisode.mypage.sideeffect.ProfileEditSideEffect
import com.boostcamp.mapisode.mypage.state.ProfileEditState
import com.boostcamp.mapisode.storage.StorageRepository
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.boostcamp.mapisode.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
	private val storageRepository: StorageRepository,
	private val groupRepository: GroupRepository,
	private val userRepository: UserRepository,
	private val userPreferenceDataStore: UserPreferenceDataStore,
) : BaseViewModel<ProfileEditIntent, ProfileEditState, ProfileEditSideEffect>(ProfileEditState()) {
	override fun onIntent(intent: ProfileEditIntent) {
		when (intent) {
			is ProfileEditIntent.Init -> initState()
			is ProfileEditIntent.NameChanged -> updateName(intent.nickname)
			is ProfileEditIntent.ProfileChanged -> updateProfileUrl(intent.url)
			is ProfileEditIntent.PhotopickerClick -> changePhotopickerClicked()
			is ProfileEditIntent.BackClick -> navigateToMypage()
			is ProfileEditIntent.EditClick -> editClick()
		}
	}

	private fun initState() {
		try {
			viewModelScope.launch {
				userPreferenceDataStore.getUserPreferencesFlow().first().let { userPreferences ->
					intent {
						copy(
							isLoading = false,
							uid = userPreferences.userId ?: throw Exception("UserId is null"),
							name = userPreferences.username ?: throw Exception("Username is null"),
							profileUrl = userPreferences.profileUrl
								?: throw Exception("ProfileUrl is null"),
						)
					}
				}
			}
		} catch (e: Exception) {
			postSideEffect(ProfileEditSideEffect.ShowToast(R.string.mypage_error_load_profile))
		}
	}

	private fun updateName(nickname: String) {
		intent {
			copy(
				name = nickname,
			)
		}
	}

	private fun updateProfileUrl(profileUrl: String) {
		intent {
			copy(
				profileUrl = profileUrl,
			)
		}
	}

	private fun changePhotopickerClicked() {
		intent {
			copy(
				isPhotoPickerClicked = !isPhotoPickerClicked,
			)
		}
	}

	private fun navigateToMypage() {
		intent {
			copy(
				isLoading = false,
			)
		}
		postSideEffect(ProfileEditSideEffect.NavigateToMypage)
	}

	private fun editClick() {
		val localUrl = currentState.profileUrl
		intent {
			copy(
				isLoading = true,
			)
		}

		try {
			viewModelScope.launch {
				updateProfileUrl(getStorageUrl())
				updateMyGroupProfileUrl(localUrl)
				storeInUserPreferenceDataStore()
				storeInUserRepository()
				navigateToMypage()
			}
		} catch (e: Exception) {
			postSideEffect(ProfileEditSideEffect.ShowToast(R.string.mypage_error_profile_edit))
		}
	}

	private suspend fun getStorageUrl(): String {
		try {
			return storageRepository.uploadSingleImageToStorage(
				imageUri = currentState.profileUrl,
				uid = currentState.uid,
			)
		} catch (e: Exception) {
			throw e
		}
	}

	private suspend fun updateMyGroupProfileUrl(url: String) {
		viewModelScope.launch {
			val myGroup = groupRepository.getGroupByGroupId(currentState.uid)
			groupRepository.updateGroup(myGroup.copy(imageUrl = url))
		}
	}

	private suspend fun storeInUserPreferenceDataStore() {
		viewModelScope.launch {
			with(userPreferenceDataStore) {
				updateUsername(currentState.name)
				updateProfileUrl(currentState.profileUrl)
			}
		}
	}

	private suspend fun storeInUserRepository() {
		viewModelScope.launch {
			userRepository.updateUserNameAndProfileUrl(
				uid = currentState.uid,
				userName = currentState.name,
				profileUrl = currentState.profileUrl,
			)
		}
	}
}
