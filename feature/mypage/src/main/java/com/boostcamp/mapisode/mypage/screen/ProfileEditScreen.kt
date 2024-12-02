package com.boostcamp.mapisode.mypage.screen

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.boostcamp.mapisode.mypage.R
import com.boostcamp.mapisode.mypage.intent.ProfileEditIntent
import com.boostcamp.mapisode.mypage.sideeffect.ProfileEditSideEffect
import com.boostcamp.mapisode.mypage.viewmodel.ProfileEditViewModel
import com.boostcamp.mapisode.ui.photopicker.MapisodePhotoPicker

@Composable
fun ProfileEditRoute(
	onBackClick: () -> Unit,
	viewModel: ProfileEditViewModel = hiltViewModel(),
) {
	val context = LocalContext.current
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	LaunchedEffect(Unit) {
		viewModel.onIntent(ProfileEditIntent.Init)
	}

	LaunchedEffect(Unit) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is ProfileEditSideEffect.NavigateToMypage -> onBackClick()
				is ProfileEditSideEffect.ShowToast -> {
					Toast.makeText(
						context,
						sideEffect.messageResId,
						Toast.LENGTH_SHORT,
					).show()
				}
			}
		}
	}

	ProfileEditScreen(
		isPhotoPickerClicked = uiState.isPhotoPickerClicked,
		onPhotoPickerClick = { viewModel.onIntent(ProfileEditIntent.PhotopickerClick) },
		nickname = uiState.name,
		onNicknameChanged = { viewModel.onIntent(ProfileEditIntent.NameChanged(it)) },
		profileUrl = uiState.profileUrl,
		onProfileUrlChange = { viewModel.onIntent(ProfileEditIntent.ProfileChanged(it)) },
		onEditClick = { viewModel.onIntent(ProfileEditIntent.EditClick) },
		onBackClick = onBackClick,
	)
}

@Composable
fun ProfileEditScreen(
	isPhotoPickerClicked: Boolean,
	onPhotoPickerClick: () -> Unit,
	nickname: String,
	onNicknameChanged: (String) -> Unit,
	profileUrl: String,
	onProfileUrlChange: (String) -> Unit,
	onEditClick: () -> Unit,
	onBackClick: () -> Unit,
	modifier: Modifier = Modifier,
) {
	if (isPhotoPickerClicked) {
		MapisodePhotoPicker(
			numOfPhoto = 1,
			onPhotoSelected = { selectedPhotos ->
				onProfileUrlChange(selectedPhotos.first().uri)
				onPhotoPickerClick()
			},
			onPermissionDenied = { onPhotoPickerClick() },
			onBackPressed = { onPhotoPickerClick() },
			isCameraNeeded = false,
		)
	} else {
		MapisodeScaffold(
			modifier = modifier.fillMaxSize(),
			isStatusBarPaddingExist = true,
			isNavigationBarPaddingExist = true,
			topBar = {
				ProfileEditTopBar(
					onBackClick = onBackClick,
				)
			},
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
						text = stringResource(R.string.mypage_nickname),
						modifier = Modifier.align(Alignment.Start),
						style = MapisodeTheme.typography.titleLarge,
					)

					Spacer(modifier = Modifier.height(12.dp))

					MapisodeTextField(
						value = nickname,
						onValueChange = { onNicknameChanged(it) },
						placeholder = stringResource(R.string.mypage_placeholder_nickname),
						modifier = Modifier
							.fillMaxWidth(),
						keyboardOptions = KeyboardOptions(
							imeAction = ImeAction.Done,
						),
					)

					Spacer(modifier = Modifier.weight(2f))

					MapisodeText(
						text = stringResource(R.string.mypage_profile_image),
						modifier = Modifier.align(Alignment.Start),
						style = MapisodeTheme.typography.titleLarge,
					)

					Spacer(modifier = Modifier.height(12.dp))

					MapisodeImageButton(
						onClick = { onPhotoPickerClick() },
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
								contentDescription = stringResource(R.string.mypage_profile_image),
								modifier = Modifier
									.fillMaxSize(),
								contentScale = ContentScale.Crop,
							)
						}
					}

					Spacer(modifier = Modifier.weight(3f))

					MapisodeFilledButton(
						text = stringResource(R.string.mypage_edit),
						onClick = onEditClick,
						modifier = Modifier
							.weight(1f)
							.fillMaxWidth(),
					)
				}
			}
		}
	}
}

@Composable
fun ProfileEditTopBar(
	onBackClick: () -> Unit,
) {
	TopAppBar(
		navigationIcon = {
			MapisodeIconButton(
				onClick = onBackClick,
			) {
				MapisodeIcon(id = drawable.ic_arrow_back_ios)
			}
		},
		title = stringResource(R.string.mypage_edit_profile),
	)
}

@Preview(
	showBackground = true,
	showSystemUi = true,
)
@Composable
fun SignUpScreenPreview() {
	MapisodeTheme {
		ProfileEditScreen(
			isPhotoPickerClicked = false,
			onPhotoPickerClick = {},
			nickname = "nickname",
			onNicknameChanged = {},
			profileUrl = "",
			onProfileUrlChange = {},
			onEditClick = {},
			onBackClick = {},
		)
	}
}
