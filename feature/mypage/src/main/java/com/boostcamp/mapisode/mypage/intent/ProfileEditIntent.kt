package com.boostcamp.mapisode.mypage.intent

import com.boostcamp.mapisode.ui.base.UiIntent

sealed interface ProfileEditIntent : UiIntent {
	data object Init : ProfileEditIntent
	data class NameChanged(val nickname: String) : ProfileEditIntent
	data class ProfileChanged(val url: String) : ProfileEditIntent
	data object PhotopickerClick : ProfileEditIntent
	data object EditClick : ProfileEditIntent
	data object BackClick : ProfileEditIntent
}
