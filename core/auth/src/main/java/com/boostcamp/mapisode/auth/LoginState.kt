package com.boostcamp.mapisode.auth

import com.boostcamp.mapisode.model.AuthData

sealed interface LoginState {
	data class Success(val idToken: String, val authDataInfo: AuthData) : LoginState
	data class Error(val message: String) : LoginState
	data object Cancel : LoginState
}
