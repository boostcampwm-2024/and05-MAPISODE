package com.boostcamp.mapisode.auth

sealed interface LoginState {
	data class Success(val idToken: String, val userInfo: UserInfo) : LoginState
	data class Error(val message: String) : LoginState
}

data class UserInfo(val firebaseUID: String, val name: String, val phoneNumber: String)
