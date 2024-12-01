package com.boostcamp.mapisode.mygroup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.Direction
import com.boostcamp.mapisode.designsystem.compose.MapisodeDivider
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.MapisodeTextField
import com.boostcamp.mapisode.designsystem.compose.Thickness
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeImageButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.mygroup.intent.GroupCreationIntent
import com.boostcamp.mapisode.mygroup.sideeffect.GroupCreationSideEffect
import com.boostcamp.mapisode.mygroup.sideeffect.rememberFlowWithLifecycle
import com.boostcamp.mapisode.mygroup.viewmodel.GroupCreationViewModel
import com.boostcamp.mapisode.ui.photopicker.MapisodePhotoPicker

@Composable
fun GroupCreationScreen(
	onBackClick: () -> Unit,
	viewModel: GroupCreationViewModel = hiltViewModel(),
) {
	val uiState = viewModel.uiState.collectAsStateWithLifecycle()
	val effect = rememberFlowWithLifecycle(
		flow = viewModel.sideEffect,
		initialValue = GroupCreationSideEffect.Idle,
	).value

	if (uiState.value.isInitializing) {
		viewModel.onIntent(GroupCreationIntent.Initialize)
	}

	LaunchedEffect(effect) {
		when (effect) {
			is GroupCreationSideEffect.NavigateToGroupScreen -> {
				onBackClick()
			}
		}
	}

	LaunchedEffect(Unit) {
		if (uiState.value.isGroupEditError) {
			viewModel.onIntent(GroupCreationIntent.OnGroupCreationError)
		}
	}

	if (uiState.value.isSelectingGroupImage) {
		MapisodePhotoPicker(
			numOfPhoto = 1,
			onPhotoSelected = { photoList ->
				viewModel.onIntent(
					GroupCreationIntent.OnGroupImageSelect(
						photoList.first().uri,
					),
				)
			},
			onPermissionDenied = { viewModel.onIntent(GroupCreationIntent.OnBackToGroupCreation) },
			onBackPressed = {
				viewModel.onIntent(GroupCreationIntent.OnBackToGroupCreation)
			},
			isCameraNeeded = false,
		)
	} else {
		GroupCreationContent(
			imageUrl = uiState.value.group.imageUrl,
			onBackClick = onBackClick,
			onGroupEditClick = { title, content, imageUrl ->
				viewModel.onIntent(
					GroupCreationIntent.OnGroupCreationClick(
						title = title,
						content = content,
						imageUrl = imageUrl,
					),
				)
			},
			onPhotoPickerClick = {
				viewModel.onIntent(GroupCreationIntent.OnPhotoPickerClick)
			},
		)
	}
}

@Composable
fun GroupCreationContent(
	imageUrl: String,
	onBackClick: () -> Unit,
	onGroupEditClick: (title: String, content: String, imageUrl: String) -> Unit,
	onPhotoPickerClick: () -> Unit,
) {
	val focusManager = LocalFocusManager.current

	MapisodeScaffold(
		modifier = Modifier
			.fillMaxSize()
			.pointerInput(Unit) {
				detectTapGestures(
					onPress = {
						focusManager.clearFocus()
					},
				)
			},
		isStatusBarPaddingExist = true,
		isNavigationBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = "그룹 생성",
				navigationIcon = {
					MapisodeIconButton(
						onClick = {
							onBackClick()
						},
					) {
						MapisodeIcon(
							id = R.drawable.ic_arrow_back_ios,
						)
					}
				},
			)
		},
	) { paddingValues ->
		GroupCreationField(
			paddingValues = paddingValues,
			imageUrl = imageUrl,
			onGroupEditClick = onGroupEditClick,
			onPhotoPickerClick = onPhotoPickerClick,
		)
	}
}

@Composable
fun GroupCreationField(
	paddingValues: PaddingValues,
	imageUrl: String,
	onGroupEditClick: (title: String, content: String, imageUrl: String) -> Unit,
	onPhotoPickerClick: () -> Unit,
) {
	var name by rememberSaveable { mutableStateOf("") }
	var description by rememberSaveable { mutableStateOf("") }
	var profileUrl by rememberSaveable { mutableStateOf("") }

	LaunchedEffect(imageUrl) {
		if (imageUrl.isNotBlank()) {
			profileUrl = imageUrl
		}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(paddingValues)
			.padding(horizontal = 20.dp),
	) {
		LazyColumn(
			modifier = Modifier
				.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(20.dp),
			contentPadding = PaddingValues(vertical = 10.dp),
		) {
			item {
				Column {
					MapisodeText(
						text = "그룹 대표 이미지",
						style = MapisodeTheme.typography.titleMedium
							.copy(fontWeight = FontWeight.SemiBold),
					)

					Spacer(modifier = Modifier.padding(4.dp))

					MapisodeImageButton(
						modifier = Modifier
							.sizeIn(maxWidth = 380.dp, maxHeight = 380.dp)
							.fillMaxWidth()
							.aspectRatio(1f),
						onClick = { onPhotoPickerClick() },
						showImage = false,
						text = "이미지를 선택하세요",
					) {
						AsyncImage(
							model = profileUrl,
							contentDescription = "그룹 이미지",
							modifier = Modifier
								.fillMaxSize()
								.aspectRatio(1f),
						)
					}
				}
			}
			item {
				Column(
					modifier = Modifier
						.sizeIn(maxWidth = 380.dp, maxHeight = 380.dp)
						.fillMaxWidth(),
				) {
					MapisodeText(
						text = "이름",
						style = MapisodeTheme.typography.titleMedium
							.copy(fontWeight = FontWeight.SemiBold),
					)

					Spacer(modifier = Modifier.padding(4.dp))

					MapisodeTextField(
						value = name,
						onValueChange = {
							name = it
						},
						modifier = Modifier.fillMaxWidth(),
					)
				}
			}
			item {
				Column(
					modifier = Modifier
						.sizeIn(maxWidth = 380.dp, maxHeight = 380.dp)
						.fillMaxWidth(),
				) {
					MapisodeText(
						text = "설명",
						style = MapisodeTheme.typography.titleMedium
							.copy(fontWeight = FontWeight.SemiBold),
					)

					Spacer(modifier = Modifier.padding(4.dp))

					MapisodeTextField(
						value = description,
						onValueChange = {
							description = it
						},
						modifier = Modifier
							.heightIn(min = 100.dp, max = 300.dp)
							.fillMaxWidth(),
					)

					Spacer(modifier = Modifier.padding(40.dp))
				}
			}
		}
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.align(Alignment.BottomCenter)
				.background(MapisodeTheme.colorScheme.surfaceBackground),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Spacer(modifier = Modifier.padding(top = 4.dp))
			MapisodeDivider(direction = Direction.Horizontal, thickness = Thickness.Thin)
			Spacer(modifier = Modifier.padding(5.dp))
			MapisodeFilledButton(
				modifier = Modifier
					.sizeIn(maxWidth = 380.dp, maxHeight = 80.dp)
					.fillMaxWidth()
					.heightIn(52.dp),
				onClick = {
					onGroupEditClick(name, description, profileUrl)
				},
				text = "생성하기",
				showRipple = true,
			)
		}
	}
}
