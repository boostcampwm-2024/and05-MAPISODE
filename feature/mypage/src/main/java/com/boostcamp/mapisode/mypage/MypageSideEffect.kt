package com.boostcamp.mapisode.mypage

import com.boostcamp.mapisode.ui.base.SideEffect

sealed interface MypageSideEffect : SideEffect {
	data object Idle : MypageSideEffect
	data class ShowToast(val messageResId: Int) : MypageSideEffect
	data object NavigateToLoginScreen : MypageSideEffect
	data object NavigateToEditScreen : MypageSideEffect
}

sealed interface ProfileEditSideEffect : SideEffect {
	data object Idle : ProfileEditSideEffect
	data class ShowToast(val messageResId: Int) : ProfileEditSideEffect
	data object NavigateToMypage : ProfileEditSideEffect
}
