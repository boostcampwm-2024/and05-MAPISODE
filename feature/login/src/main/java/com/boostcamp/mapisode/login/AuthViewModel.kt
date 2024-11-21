package com.boostcamp.mapisode.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.auth.GoogleOauth
import com.boostcamp.mapisode.auth.LoginState
import com.boostcamp.mapisode.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

	private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
	val uiState = _uiState.asStateFlow()

	fun handleGoogleSignIn(context: Context) {
		viewModelScope.launch {
			_uiState.value = AuthUiState.Loading
			GoogleOauth(context).googleSignIn().collect { result ->
				_uiState.value = when (result) {
					is LoginState.Success -> {
						Timber.e("result: ${result.userInfo}")
						AuthUiState.Success(
							User(
								id = result.userInfo.firebaseUID,
								displayName = result.userInfo.name,
							),
						)
					}

					is LoginState.Error -> {
						AuthUiState.Error("Sign-in failed")
					}
				}
			}
		}
	}
}
