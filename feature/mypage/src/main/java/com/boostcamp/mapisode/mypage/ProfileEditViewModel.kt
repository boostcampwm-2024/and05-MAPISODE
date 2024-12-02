package com.boostcamp.mapisode.mypage

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.boostcamp.mapisode.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
	private val userRepository: UserRepository,
	private val userPreferenceDataStore: UserPreferenceDataStore,
) : BaseViewModel<ProfileEditIntent, ProfileEditState, ProfileEditSideEffect>(ProfileEditState()) {
	override fun onIntent(intent: ProfileEditIntent) {
		when (intent) {
			is ProfileEditIntent.Init -> initState()
			is ProfileEditIntent.BackClick -> navigateToMypage()
			is ProfileEditIntent.NameChanged -> updateName(intent.nickname)
			is ProfileEditIntent.ProfileChanged -> updateProfileUrl(intent.url)
			is ProfileEditIntent.PhotopickerClick -> changePhotopickerClicked()
			is ProfileEditIntent.EditClick -> editClick()
		}
	}

	private fun initState() {
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

	private fun navigateToMypage() {
		postSideEffect(ProfileEditSideEffect.NavigateToMypage)
	}

	private fun changePhotopickerClicked() {
		intent {
			copy(
				isPhotoPickerClicked = !isPhotoPickerClicked,
			)
		}
	}

	private fun editClick() {
		try {
			viewModelScope.launch {
				storeInUserPreferenceDataStore()
				storeInUserRepository()
				navigateToMypage()
			}
		} catch (e: Exception) {
			postSideEffect(ProfileEditSideEffect.ShowToast(R.string.mypage_error_profile_edit))
		}
	}

	private suspend fun storeInUserPreferenceDataStore() {
		viewModelScope.launch {
			with(userPreferenceDataStore) {
				updateUsername(currentState.name)
				updateProfileUrl(currentState.profileUrl)
			}
		}.join()
	}

	private suspend fun storeInUserRepository() {
		viewModelScope.launch {
			userRepository.updateUserNameAndProfileUrl(
				uid = currentState.uid,
				userName = currentState.name,
				profileUrl = currentState.profileUrl,
			)
		}.join()
	}
}
