package com.boostcamp.mapisode.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

	LaunchedEffect(Unit) {
		viewModel.onIntent(AuthIntent.OnAutoLogin)
	}

	LaunchedEffect(Unit) {
		viewModel.sideEffect.collect { effect ->
			when (effect) {
				is AuthSideEffect.NavigateToMain -> navigateToMain()
				is AuthSideEffect.ShowError -> {
					Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
				}
			}
		}
	}

	when {
		uiState.isLoading -> {
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S) {
				SplashScreen()
			}
		}

		uiState.isLoginSuccess -> {
			SignUpScreen(
				nickname = uiState.nickname,
				onNicknameChanged = { newNickname ->
					viewModel.onIntent(AuthIntent.OnNicknameChange(newNickname))
				},
				profileUrl = uiState.profileUrl,
				onProfileUrlchange = { profileUrl ->
					viewModel.onIntent(AuthIntent.OnProfileUrlchange(profileUrl))
				},
				onSignUpClick = { viewModel.onIntent(AuthIntent.OnSignUpClick) },
				onBackClickedInSignUp = { viewModel.onIntent(AuthIntent.OnBackClickedInSignUp) },
			)
		}

		else -> {
			LoginScreen(
				googleSignInClicked = {
					viewModel.onIntent(AuthIntent.OnGoogleSignInClick(googleOauth))
				},
			)
		}
	}
}
