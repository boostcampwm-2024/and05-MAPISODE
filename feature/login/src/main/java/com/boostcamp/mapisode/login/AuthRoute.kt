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

	// 빌드 통과하게 만들기 위해 추가한 더미 코드
	val isLoginSuccess = true
	val nickname = "nickname"

	if (isLoginSuccess) {
		SignUpScreen(
			nickname = nickname,
			onNicknameChanged = { },
			onSignUpClick = { },
		)
	} else {
		LoginScreen(
			googleSignInClicked = { },
		)
	}
}
