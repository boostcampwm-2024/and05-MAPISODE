package com.boostcamp.mapisode.login

import com.boostcamp.mapisode.model.AuthData
import com.boostcamp.mapisode.ui.base.UiState

data class AuthState(
	val showSplash: Boolean = true,
	val isLoading: Boolean = false,
	val isLoginSuccess: Boolean = false,
	val authData: AuthData? = null,
	val nickname: String = "",
	val profileUrl: String = "",
	val isNicknameValid: Boolean = false,
) : UiState
