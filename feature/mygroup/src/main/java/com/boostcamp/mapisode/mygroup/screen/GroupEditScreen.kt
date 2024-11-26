package com.boostcamp.mapisode.mygroup.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeImageButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.mygroup.intent.GroupEditIntent
import com.boostcamp.mapisode.mygroup.sideeffect.GroupEditSideEffect
import com.boostcamp.mapisode.mygroup.sideeffect.rememberFlowWithLifecycle
import com.boostcamp.mapisode.mygroup.viewmodel.GroupEditViewModel
import com.boostcamp.mapisode.navigation.GroupRoute

@Composable
fun GroupEditScreen(
	edit: GroupRoute.Edit,
	onBackClick: () -> Unit,
	viewModel: GroupEditViewModel = hiltViewModel(),
) {
	val uiState = viewModel.uiState.collectAsStateWithLifecycle()
	val effect = rememberFlowWithLifecycle(
		flow = viewModel.sideEffect,
		initialValue = GroupEditSideEffect.Idle,
	).value

	LaunchedEffect(uiState.value) {
		if(uiState.value.isInitializing) {
			viewModel.onIntent(GroupEditIntent.LoadGroups(edit.groupId))
		}
	}

	LaunchedEffect(effect) {
		when (effect) {
			is GroupEditSideEffect.NavigateToGroupDetailScreen -> {
				onBackClick()
			}
		}
	}

	GroupEditContent(
		onBackClick = onBackClick,
	)
}

@Composable
fun GroupEditContent(
	onBackClick: () -> Unit,
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
		topBar = {
			TopAppBar(
				title = "그룹 편집",
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
	) {
		var isClicked by remember { mutableStateOf(true) }
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(it),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			MapisodeImageButton(
				modifier = Modifier
					.fillMaxWidth(0.8f)
					.aspectRatio(1f),
				onClick = { isClicked = !isClicked },
				showImage = isClicked,
				text = "이미지를 선택하세요",
			) {
				AsyncImage(
					model = "https://m.media-amazon.com/images/I/61jyqnlyIaS.jpg",
					contentDescription = "그룹 이미지",
					modifier = Modifier.fillMaxSize(),
				)
			}
		}
	}
}
