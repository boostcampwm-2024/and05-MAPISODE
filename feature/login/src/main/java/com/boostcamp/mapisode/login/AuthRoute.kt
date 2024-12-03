package com.boostcamp.mapisode.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.auth.GoogleOauth
import com.boostcamp.mapisode.designsystem.compose.MapisodeCircularLoadingIndicator

@Composable
fun AuthRoute(
	navigateToMain: () -> Unit,
	viewModel: AuthViewModel = hiltViewModel(),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val context = LocalContext.current
	val googleOauth = GoogleOauth(context)

	LaunchedEffect(Unit) {
		viewModel.onIntent(AuthIntent.Init)
	}

	LaunchedEffect(Unit) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is AuthSideEffect.NavigateToMain -> navigateToMain()
				is AuthSideEffect.ShowToast -> {
					Toast.makeText(
						context,
						context.getString(sideEffect.messageId),
						Toast.LENGTH_SHORT,
					).show()
				}
			}
		}
	}

	when {
		uiState.showSplash -> {
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S) {
				SplashScreen()
			}
		}

		uiState.isLoading -> {
			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center,
			) {
				MapisodeCircularLoadingIndicator()
			}
		}

		uiState.isLoginSuccess -> {
			SignUpScreen(
				nickname = uiState.nickname,
				onNicknameChanged = { newNickname ->
					viewModel.onIntent(AuthIntent.OnNicknameChange(newNickname))
				},
				profileUrl = uiState.profileUrl,
				onProfileUrlChange = { profileUrl ->
					viewModel.onIntent(AuthIntent.OnProfileUrlChange(profileUrl))
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
