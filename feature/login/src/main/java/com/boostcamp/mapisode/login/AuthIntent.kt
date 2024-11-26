package com.boostcamp.mapisode.login

import com.boostcamp.mapisode.auth.GoogleOauth
import com.boostcamp.mapisode.ui.base.UiIntent

sealed interface AuthIntent : UiIntent {
	data class OnGoogleSignInClick(val googleOauth: GoogleOauth) : AuthIntent
	data class OnNicknameChange(val nickname: String) : AuthIntent
	data object OnSignUpClick : AuthIntent
	data object OnAutoLogin : AuthIntent
	data object OnLoginSuccess : AuthIntent
}
