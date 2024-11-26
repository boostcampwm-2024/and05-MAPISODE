package com.boostcamp.mapisode.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.auth.GoogleOauth

@Composable
fun AuthRoute(
	navigateToMain: () -> Unit,
	viewModel: AuthViewModel = hiltViewModel(),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val context = LocalContext.current
	val googleOauth = GoogleOauth(context)

	if (uiState.isLoginSuccess) {
		SignUpScreen(
			nickname = uiState.nickname,
			onNicknameChanged = { newNickname ->
				viewModel.onIntent(AuthIntent.OnNicknameChange(newNickname))
			},
			onSignUpClick = { viewModel.onIntent(AuthIntent.OnSignUpClick) },
		)
	} else {
		LoginScreen(
			googleSignInClicked = {
				viewModel.onIntent(AuthIntent.OnGoogleSignInClick(googleOauth))
			},
		)
	}
}
