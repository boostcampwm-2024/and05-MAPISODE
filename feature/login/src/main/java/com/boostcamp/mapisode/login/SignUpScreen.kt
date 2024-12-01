package com.boostcamp.mapisode.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.designsystem.R.drawable
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.MapisodeTextField
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeImageButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.ui.photopicker.MapisodePhotoPicker

@Composable
fun SignUpScreen(
	nickname: String,
	onNicknameChanged: (String) -> Unit,
	profileUrl: String,
	onProfileUrlchange: (String) -> Unit,
	onSignUpClick: () -> Unit,
	onBackClickedInSignUp: () -> Unit,
	modifier: Modifier = Modifier,
) {
	val isPhotoPickerClicked = rememberSaveable { mutableStateOf(false) }

	BackHandler {
		if (isPhotoPickerClicked.value) {
			isPhotoPickerClicked.value = false
		} else {
			onBackClickedInSignUp()
		}
	}

	if (isPhotoPickerClicked.value) {
		MapisodePhotoPicker(
			numOfPhoto = 1,
			onPhotoSelected = { selectedPhotos ->
				onProfileUrlchange(selectedPhotos[0].uri)
				isPhotoPickerClicked.value = false
			},
			onPermissionDenied = { isPhotoPickerClicked.value = false },
			onBackPressed = { isPhotoPickerClicked.value = false },
			isCameraNeeded = true,
		)
	} else {
		MapisodeScaffold(
			modifier = modifier.fillMaxSize(),
			isStatusBarPaddingExist = true,
			isNavigationBarPaddingExist = true,
			topBar = { SignUpTopBar(onBackClickedInSignUp) },
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

					MapisodeImageButton(
						onClick = { isPhotoPickerClicked.value = true },
						showImage = profileUrl.isBlank(),
						modifier = Modifier
							.fillMaxWidth()
							.aspectRatio(1f),
					) {
						Column(
							modifier = Modifier.fillMaxSize(),
							horizontalAlignment = Alignment.CenterHorizontally,
							verticalArrangement = Arrangement.Center,
						) {
							AsyncImage(
								model = profileUrl,
								contentDescription = "프로필 이미지",
								modifier = Modifier
									.fillMaxSize(),
								contentScale = ContentScale.Crop,
							)
						}
					}

					Spacer(modifier = Modifier.weight(3f))

					MapisodeFilledButton(
						text = stringResource(R.string.login_next),
						onClick = onSignUpClick,
						modifier = Modifier
							.weight(1f)
							.fillMaxWidth(),
					)

					Spacer(modifier = Modifier.weight(0.6f))
				}
			}
		}
	}
}

@Composable
fun SignUpTopBar(
	onBackClickedInSignUp: () -> Unit,
) {
	TopAppBar(
		navigationIcon = {
			MapisodeIconButton(
				onClick = { onBackClickedInSignUp() },
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
			nickname = "닉네임",
			onNicknameChanged = {},
			profileUrl = "",
			onProfileUrlchange = {},
			onSignUpClick = {},
			onBackClickedInSignUp = {},
		)
	}
}
