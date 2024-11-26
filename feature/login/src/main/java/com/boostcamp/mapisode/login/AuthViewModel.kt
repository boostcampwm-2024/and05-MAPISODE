package com.boostcamp.mapisode.login

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.auth.GoogleOauth
import com.boostcamp.mapisode.auth.LoginState
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.model.UserModel
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.boostcamp.mapisode.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
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

	private fun handleSignUp() {
		viewModelScope.launch {
			try {
				userRepository.createUser(
					UserModel(
						uid = uiState.value.authData?.uid ?: "",
						email = uiState.value.authData?.email ?: "",
						name = uiState.value.nickname,
						profileUrl = uiState.value.profileUri,
						joinedAt = Date.from(java.time.Instant.now()),
						groups = emptyList(),
					),
				)

				userDataStore.updateCredentialIdToken(uiState.value.authData?.idToken ?: "")
				userDataStore.updateUserId(uiState.value.authData?.uid ?: "")
				userDataStore.updateUsername(uiState.value.nickname)
				userDataStore.updateProfileUrl(uiState.value.profileUri)
				userDataStore.updateIsLoggedIn(true)
				userDataStore.updateIsFirstLaunch()

				postSideEffect(AuthSideEffect.NavigateToMain)
			} catch (e: Exception) {
				postSideEffect(AuthSideEffect.ShowError(e.message ?: "회원가입에 실패했습니다."))
			}
		}
	}
}
