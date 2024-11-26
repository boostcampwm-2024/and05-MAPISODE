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
			is AuthIntent.OnNicknameChange -> onNicknameChange(intent.nickname)
			is AuthIntent.OnSignUpClick -> handleSignUp()
			is AuthIntent.OnAutoLogin -> handleAutoLogin()
			is AuthIntent.OnLoginSuccess -> handleLoginSuccess()
		}
	}

	private fun handleAutoLogin() {
		viewModelScope.launch {
			if (userDataStore.checkLoggedIn()) {
				onIntent(AuthIntent.OnLoginSuccess)
			}
		}
	}

	private suspend fun isUserExist(uid: String): Boolean = try {
		userRepository.isUserExist(uid)
	} catch (e: Exception) {
		false
	}

	private fun handleGoogleSignIn(googleOauth: GoogleOauth) {
		viewModelScope.launch {
			try {
				googleOauth.googleSignIn()
					.collect { loginState ->
						when (loginState) {
							is LoginState.Success -> {
								if (isUserExist(loginState.authDataInfo.uid)) {
									onIntent(AuthIntent.OnLoginSuccess)
									return@collect
								}

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
				if (uiState.value.nickname.isBlank()) throw IllegalArgumentException("닉네임을 입력해주세요.")
				if (uiState.value.profileUrl.isBlank()) throw IllegalArgumentException("프로필 사진을 선택해주세요.")
				if (uiState.value.authData == null) throw IllegalArgumentException("로그인 정보가 없습니다.")

				userRepository.createUser(
					UserModel(
						uid = uiState.value.authData?.uid
							?: throw IllegalArgumentException("UID cannot be empty"),
						email = uiState.value.authData?.email
							?: throw IllegalArgumentException("Email cannot be empty"),
						name = uiState.value.nickname,
						profileUrl = uiState.value.profileUrl,
						joinedAt = Date.from(java.time.Instant.now()),
						groups = emptyList(),
					),
				)

				with(userDataStore) {
					updateCredentialIdToken(
						uiState.value.authData?.idToken
							?: throw IllegalArgumentException("로그인 정보가 없습니다."),
					)
					updateUserId(
						uiState.value.authData?.uid
							?: throw IllegalArgumentException("로그인 정보가 없습니다."),
					)
					updateUsername(uiState.value.nickname)
					updateProfileUrl(uiState.value.profileUrl)
					updateIsFirstLaunch()
				}

				onIntent(AuthIntent.OnLoginSuccess)
			} catch (e: Exception) {
				postSideEffect(AuthSideEffect.ShowError(e.message ?: "회원가입에 실패했습니다."))
			}
		}
	}

	private fun handleLoginSuccess() {
		viewModelScope.launch {
			userDataStore.updateIsLoggedIn(true)
		}
		postSideEffect(AuthSideEffect.NavigateToMain)
	}
}
