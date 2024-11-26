package com.boostcamp.mapisode.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R.drawable
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.MapisodeTextField
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeOutlinedButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun SignUpScreen(
	nickname: String,
	onNicknameChanged: (String) -> Unit,
	onSignUpClick: () -> Unit,
	modifier: Modifier = Modifier,
) {
	MapisodeScaffold(
		modifier = modifier.fillMaxSize(),
		isStatusBarPaddingExist = true,
		isNavigationBarPaddingExist = true,
		topBar = { SignUpTopBar() },
	) { paddingValues ->
		Box(
			modifier = modifier
				.padding(paddingValues)
				.fillMaxSize(),
			contentAlignment = Alignment.Center,
		) {
			Column(
				modifier = Modifier
					.fillMaxWidth(0.85f),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Spacer(modifier = Modifier.weight(0.5f))

				MapisodeText(
					text = stringResource(R.string.login_nickname),
					modifier = Modifier.align(Alignment.Start),
					style = MapisodeTheme.typography.titleLarge,
				)

				Spacer(modifier = Modifier.height(12.dp))

				MapisodeTextField(
					value = nickname,
					onValueChange = onNicknameChanged,
					placeholder = stringResource(R.string.login_nickname_placeholder),
					modifier = Modifier
						.fillMaxWidth(),
					keyboardOptions = KeyboardOptions(
						imeAction = ImeAction.Done,
					),
				)

				Spacer(modifier = Modifier.weight(2f))

				MapisodeText(
					text = stringResource(R.string.login_profile_image),
					modifier = Modifier.align(Alignment.Start),
					style = MapisodeTheme.typography.titleLarge,
				)

				Spacer(modifier = Modifier.height(12.dp))

				MapisodeOutlinedButton(
					text = "placeholder for 이미지를 업로드해주세요",
					onClick = { },
					modifier = Modifier
						.fillMaxWidth()
						.aspectRatio(1f),
				)

				Spacer(modifier = Modifier.weight(3f))

				MapisodeFilledButton(
					text = stringResource(R.string.login_next),
					onClick = onSignUpClick,
					modifier = Modifier
						.fillMaxWidth(),
				)
			}
		}
	}
}

@Composable
fun SignUpTopBar() {
	TopAppBar(
		navigationIcon = {
			MapisodeIconButton(
				onClick = { },
			) {
				MapisodeIcon(id = drawable.ic_arrow_back_ios)
			}
		},
		title = stringResource(R.string.login_signup),
	)
}

@Preview(
	showBackground = true,
	showSystemUi = true,
	widthDp = 360,
	heightDp = 780,
)
@Composable
fun SignUpScreenPreview() {
	MapisodeTheme {
		SignUpScreen(
			nickname = "nickname",
			onNicknameChanged = { },
			onSignUpClick = { },
		)
	}
}
