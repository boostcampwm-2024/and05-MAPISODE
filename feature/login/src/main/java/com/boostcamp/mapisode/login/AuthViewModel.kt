package com.boostcamp.mapisode.login

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.auth.GoogleOauth
import com.boostcamp.mapisode.auth.LoginState
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.boostcamp.mapisode.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
	private val userRepository: UserRepository,
	private val userDataStore: UserPreferenceDataStore,
) : BaseViewModel<AuthIntent, AuthState, AuthSideEffect>(AuthState()) {

	override fun onIntent(intent: AuthIntent) {
		when (intent) {
			is AuthIntent.OnGoogleSignInClick -> handleGoogleSignIn(intent.googleOauth)

			is AuthIntent.OnNicknameChange -> {
				onNicknameChange(intent.nickname)
			}

			is AuthIntent.OnSignUpClick -> handleSignUp()
		}
	}

	private fun handleGoogleSignIn(googleOauth: GoogleOauth) {
		viewModelScope.launch {
			try {
				googleOauth.googleSignIn()
					.collect { loginState ->
						when (loginState) {
							is LoginState.Success -> {

								intent {
									copy(
										isLoginSuccess = true,
										authData = loginState.authDataInfo,
									)
								}
							}

							is LoginState.Error -> {
								postSideEffect(AuthSideEffect.ShowError(loginState.message))
							}
						}
					}
			} catch (e: Exception) {
				postSideEffect(AuthSideEffect.ShowError(e.message ?: "알 수 없는 오류가 발생했습니다."))
			}
		}
	}

	private fun onNicknameChange(nickname: String) {
		intent {
			copy(
				nickname = nickname,
			)
		}
	}

}
