package com.boostcamp.mapisode.login

import com.boostcamp.mapisode.model.User

sealed class AuthUiState {
	data object Initial : AuthUiState()
	data object Loading : AuthUiState()
	data class Success(val user: User) : AuthUiState()
	data class Error(val message: String) : AuthUiState()
}
