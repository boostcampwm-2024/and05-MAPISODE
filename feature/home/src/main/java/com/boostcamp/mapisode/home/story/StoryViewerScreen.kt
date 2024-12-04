package com.boostcamp.mapisode.home.story

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.home.detail.EpisodeDetailViewModel
import com.boostcamp.mapisode.ui.story.StoryViewer
import kotlinx.collections.immutable.toPersistentList

@Composable
fun StoryViewerRoute(
	viewModel: EpisodeDetailViewModel = hiltViewModel(),
	onBackClick: () -> Unit = {},
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	val context = LocalContext.current
	val window = (context as? Activity)?.window

	DisposableEffect(Unit) {
		// 몰입 모드 활성화
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			val windowInsetsController = window?.insetsController
			windowInsetsController?.systemBarsBehavior =
				WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
			windowInsetsController?.hide(WindowInsets.Type.systemBars())
		} else {
			@Suppress("DEPRECATION")
			window?.decorView?.systemUiVisibility = (
				View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
					or View.SYSTEM_UI_FLAG_FULLSCREEN
					or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				)
		}

		onDispose {
			// 몰입 모드 비활성화
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
				val windowInsetsController = window?.insetsController
				windowInsetsController?.show(WindowInsets.Type.systemBars())
			} else {
				@Suppress("DEPRECATION")
				window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
			}
		}
	}

	StoryViewer(
		imageUrls = uiState.episode.imageUrls.toPersistentList(),
		authorName = uiState.author?.name ?: "UNKNOWN",
		authorProfileUrl = uiState.author?.profileUrl ?: "",
		onClose = onBackClick,
	)
}
