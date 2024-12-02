package com.boostcamp.mapisode.mypage.sideeffect

import com.boostcamp.mapisode.ui.base.SideEffect

sealed interface MypageSideEffect : SideEffect {
	data object Idle : MypageSideEffect
	data class ShowToast(val messageResId: Int) : MypageSideEffect
	data object NavigateToLoginScreen : MypageSideEffect
	data object NavigateToEditScreen : MypageSideEffect
	data object OpenPrivacyPolicy : MypageSideEffect
}
