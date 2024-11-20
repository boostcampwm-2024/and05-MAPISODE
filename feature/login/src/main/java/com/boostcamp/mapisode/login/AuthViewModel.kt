package com.boostcamp.mapisode.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.auth.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
	private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
) : ViewModel() {

	private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
	val uiState = _uiState.asStateFlow()

	fun handleGoogleSignIn() {
		viewModelScope.launch {
			_uiState.value = AuthUiState.Loading
			signInWithGoogleUseCase().collect { result ->
				_uiState.value = when {
					result.isSuccess -> {
						val user = result.getOrNull()
						AuthUiState.Success(user)
					}

					else -> {
						AuthUiState.Error("Sign-in failed")
					}
				}
			}
		}
	}
}
