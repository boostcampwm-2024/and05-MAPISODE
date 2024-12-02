package com.boostcamp.mapisode.mypage.viewmodel

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.auth.GoogleOauth
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.mypage.R
import com.boostcamp.mapisode.mypage.intent.MypageIntent
import com.boostcamp.mapisode.mypage.sideeffect.MypageSideEffect
import com.boostcamp.mapisode.mypage.state.MypageState
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
	private val userPreferenceDataStore: UserPreferenceDataStore,
) : BaseViewModel<MypageIntent, MypageState, MypageSideEffect>(MypageState()) {

	override fun onIntent(intent: MypageIntent) {
		when (intent) {
			is MypageIntent.Init -> initState()
			is MypageIntent.LogoutClick -> handleLogoutClick(intent.googleOauth)
			is MypageIntent.ProfileEditClick -> handleProfileEditClick()
			is MypageIntent.PrivacyPolicyClick -> handlePrivacyPolicyClick()
			is MypageIntent.WithdrawalClick -> handleWithdrawalClick()
			is MypageIntent.TurnOffDialog -> turnOffDialog()
			is MypageIntent.ConfirmClick -> handleConfirmClick(intent.googleOauth)
		}
	}

	private fun initState() {
		try {
			viewModelScope.launch {
				userPreferenceDataStore.getUserPreferencesFlow().first().let { userPreferences ->
					intent {
						copy(
							isLoading = false,
							name = userPreferences.username ?: throw Exception("Username is null"),
							profileUrl = userPreferences.profileUrl
								?: throw Exception("ProfileUrl is null"),
						)
					}
				}
			}
		} catch (e: Exception) {
			postSideEffect(MypageSideEffect.ShowToast(R.string.mypage_error_load_profile))
		}
	}

	private fun handleLogoutClick(googleOAuth: GoogleOauth) {
		logout(googleOAuth)
		postSideEffect(MypageSideEffect.ShowToast(R.string.mypage_logout_success))
		postSideEffect(MypageSideEffect.NavigateToLoginScreen)
	}

	private fun logout(googleOAuth: GoogleOauth) {
		viewModelScope.launch {
			userPreferenceDataStore.clearUserData()
			googleOAuth.googleSignOut()
		}
	}

	private fun handleProfileEditClick() {
		postSideEffect(MypageSideEffect.NavigateToEditScreen)
	}

	private fun handlePrivacyPolicyClick() {
		postSideEffect(MypageSideEffect.OpenPrivacyPolicy)
	}

	private fun handleWithdrawalClick() {
		intent {
			copy(showWithdrawalDialog = true)
		}
	}

	private suspend fun withdrawal(googleOAuth: GoogleOauth) {
		viewModelScope.launch {
			googleOAuth.deleteCurrentUser()
			userPreferenceDataStore.clearUserData()
		}.join()
	}

	private fun turnOffDialog() {
		intent {
			copy(showWithdrawalDialog = false)
		}
	}

	private fun handleConfirmClick(googleOAuth: GoogleOauth) {
		viewModelScope.launch {
			withdrawal(googleOAuth)
			postSideEffect(MypageSideEffect.NavigateToLoginScreen)
		}
	}
}
