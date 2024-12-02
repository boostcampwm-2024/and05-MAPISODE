package com.boostcamp.mapisode.mypage.sideeffect

import com.boostcamp.mapisode.ui.base.SideEffect

sealed interface ProfileEditSideEffect : SideEffect {
	data object Idle : ProfileEditSideEffect
	data class ShowToast(val messageResId: Int) : ProfileEditSideEffect
	data object NavigateToMypage : ProfileEditSideEffect
	data class OpenPrivacyPolicy(val useCustomTab: Boolean) : ProfileEditSideEffect
}
