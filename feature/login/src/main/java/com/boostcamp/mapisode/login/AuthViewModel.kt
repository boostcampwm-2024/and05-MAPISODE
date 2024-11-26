package com.boostcamp.mapisode.login

import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.boostcamp.mapisode.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

}
