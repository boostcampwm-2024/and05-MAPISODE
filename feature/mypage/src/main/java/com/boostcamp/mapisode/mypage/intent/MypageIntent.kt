package com.boostcamp.mapisode.mypage.intent

import android.content.Context
import com.boostcamp.mapisode.auth.GoogleOauth
import com.boostcamp.mapisode.ui.base.UiIntent

sealed interface MypageIntent : UiIntent {
	data object Init : MypageIntent
	data class LogoutClick(val googleOauth: GoogleOauth) : MypageIntent
	data object ProfileEditClick : MypageIntent
	data class PrivacyPolicyClick(val context: Context, val useCustomTab: Boolean) : MypageIntent
	data object WithdrawalClick : MypageIntent
	data class ConfirmClick(val googleOauth: GoogleOauth) : MypageIntent
	data object TurnOffDialog : MypageIntent
}
