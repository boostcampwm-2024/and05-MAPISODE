package com.boostcamp.mapisode.login

import com.boostcamp.mapisode.auth.GoogleOauth
import com.boostcamp.mapisode.ui.base.UiIntent

sealed interface AuthIntent : UiIntent {
	data object Init : AuthIntent
	data class OnGoogleSignInClick(val googleOauth: GoogleOauth) : AuthIntent
	data class OnNicknameChange(val nickname: String) : AuthIntent
	data class OnProfileUrlchange(val profileUrl: String) : AuthIntent
	data object OnSignUpClick : AuthIntent
	data object OnLoginSuccess : AuthIntent
	data object OnBackClickedInSignUp : AuthIntent
}
