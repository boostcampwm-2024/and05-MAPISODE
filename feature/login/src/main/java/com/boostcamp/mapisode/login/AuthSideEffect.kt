package com.boostcamp.mapisode.login

import androidx.annotation.StringRes
import com.boostcamp.mapisode.ui.base.SideEffect

sealed interface AuthSideEffect : SideEffect {
	data object NavigateToMain : AuthSideEffect
	data class ShowToast(@StringRes val messageId: Int) : AuthSideEffect
}
