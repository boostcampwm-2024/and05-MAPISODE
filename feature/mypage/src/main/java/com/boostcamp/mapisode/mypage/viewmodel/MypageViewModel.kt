package com.boostcamp.mapisode.mypage.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
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
			is MypageIntent.PrivacyPolicyClick -> handlePrivacyPolicyClick(
				intent.context,
				intent.useCustomTab,
			)

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

	private fun handlePrivacyPolicyClick(
		context: Context,
		useCustomTab: Boolean,
	) {
		openPrivacyPolicy(context, useCustomTab)
	}

	private fun openPrivacyPolicy(context: Context, useCustomTab: Boolean) {
		val url = "https://pricey-visitor-e41.notion.site/14e94239a962805b9dadf87c68353909?pvs=4"
		try {
			if (useCustomTab) {
				val customTabsIntent = CustomTabsIntent.Builder()
					.setShowTitle(true)
					.build()
				customTabsIntent.launchUrl(context, Uri.parse(url))
			} else {
				val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
				context.startActivity(intent)
			}
		} catch (e: Exception) {
			val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
			context.startActivity(intent)
		}
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
