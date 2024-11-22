package com.boostcamp.mapisode.login

import com.boostcamp.mapisode.model.AuthData

sealed class AuthUiState {
	data object Initial : AuthUiState()
	data object Loading : AuthUiState()
	data class Success(val authData: AuthData) : AuthUiState()
	data class Error(val message: String) : AuthUiState()
}
