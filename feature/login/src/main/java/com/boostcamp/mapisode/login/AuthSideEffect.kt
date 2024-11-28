package com.boostcamp.mapisode.login

import com.boostcamp.mapisode.ui.base.SideEffect

sealed interface AuthSideEffect : SideEffect {
	data object NavigateToMain : AuthSideEffect
	data class ShowError(val message: String) : AuthSideEffect
	data class EndSplash(val end: Boolean) : AuthSideEffect
}
