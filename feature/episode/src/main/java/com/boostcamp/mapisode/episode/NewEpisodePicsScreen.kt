package com.boostcamp.mapisode.episode

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.episode.intent.NewEpisodeIntent
import com.boostcamp.mapisode.episode.intent.NewEpisodeSideEffect
import com.boostcamp.mapisode.episode.intent.NewEpisodeViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun NewEpisodePicsScreen(
	viewModel: NewEpisodeViewModel = hiltViewModel(),
	onNavigateToInfo: () -> Unit,
) {
	val context = LocalContext.current

	LaunchedEffect(Unit) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is NewEpisodeSideEffect.ShowToast -> {
					Toast.makeText(
						context,
						sideEffect.messageResId,
						Toast.LENGTH_LONG,
					).show()
				}
			}
		}
	}

	MapisodeScaffold(
		topBar = {
			TopAppBar(
				title = stringResource(R.string.new_episode_menu_title),
				actions = {
					MapisodeIconButton(
						onClick = {},
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
				viewModel.onIntent(NewEpisodeIntent.SetEpisodePics(uris))
				if (uris.isNotEmpty()) {
					onNavigateToInfo()
				}
			}
		}
	}
}

@Composable
fun PickEpisodePhotoButton(
	onPickPhotos: (PersistentList<Uri>) -> Unit,
) {
	val photoPickLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.PickMultipleVisualMedia(4),
		onResult = { uris ->
			onPickPhotos(uris.toPersistentList())
		},
	)

	MapisodeFilledButton(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 20.dp)
			.fillMaxHeight(0.1f),
		onClick = {
			photoPickLauncher.launch(
				PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
			)
		},
		text = stringResource(R.string.new_episode_menu_add_pictures),
	)
}
