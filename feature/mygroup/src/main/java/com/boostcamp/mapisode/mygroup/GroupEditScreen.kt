package com.boostcamp.mapisode.mygroup

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.LocalMapisodeShowBotBar
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeImageButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar

@Composable
fun GroupEditScreen(
	onBackClick: () -> Unit,
	// onEpisodeClick: () -> Unit,
) {
	val focusManager = LocalFocusManager.current
	val bottomBarController = LocalMapisodeShowBotBar.current
	bottomBarController.off()

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
				title = "나의 그룹",
				navigationIcon = {
					MapisodeIconButton(
						onClick = {
							bottomBarController.on()
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
