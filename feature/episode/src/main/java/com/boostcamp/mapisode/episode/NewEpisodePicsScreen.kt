package com.boostcamp.mapisode.episode

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import timber.log.Timber

@Composable
internal fun NewEpisodePicsScreen(
	navController: NavController,
	updatePics: (List<Uri>) -> Unit,
) {
	MapisodeScaffold(
		topBar = {
			TopAppBar(
				title = stringResource(R.string.new_episode_menu_title),
				actions = {
					MapisodeIconButton(
						onClick = {
							navController.navigate("new_episode_info")
						},
						enabled = false,
					) {
						MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_forward_ios)
					}
				},
			)
		},
		isStatusBarPaddingExist = true,
	) { innerPadding ->
		Box(
			Modifier
				.fillMaxSize()
				.padding(innerPadding),
			contentAlignment = Alignment.Center,
		) {
			PickEpisodePhotoButton { uris ->
				updatePics(uris)
				if (uris.isNotEmpty()) {
					navController.navigate("new_episode_info")
				}
			}
		}
	}
}

@Composable
fun PickEpisodePhotoButton(
	onPickPhotos: (List<Uri>) -> Unit,
) {
	val photoPickLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.PickMultipleVisualMedia(4),
		onResult = { uris ->
			Timber.d(uris.toString())
			onPickPhotos(uris)
		},
	)

	MapisodeFilledButton(
		onClick = {
			photoPickLauncher.launch(
				PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
			)
		},
		text = "사진 추가",
	)
}
