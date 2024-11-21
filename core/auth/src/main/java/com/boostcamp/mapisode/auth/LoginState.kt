package com.boostcamp.mapisode.auth

import com.boostcamp.mapisode.model.User

sealed interface LoginState {
	data class Success(val idToken: String, val userInfo: User) : LoginState
	data class Error(val message: String) : LoginState
}
