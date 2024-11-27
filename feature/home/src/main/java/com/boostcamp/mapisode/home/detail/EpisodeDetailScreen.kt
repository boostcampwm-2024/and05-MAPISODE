package com.boostcamp.mapisode.home.detail

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import timber.log.Timber

@Composable
internal fun EpisodeDetailRoute(
	episodeId: String,
	viewModel: EpisodeDetailViewModel = hiltViewModel(),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val context = LocalContext.current

	LaunchedEffect(Unit) {
		viewModel.onIntent(EpisodeDetailIntent.LoadEpisodeDetail(episodeId))
	}

	LaunchedEffect(Unit) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is EpisodeDetailSideEffect.ShowToast -> {
					val message = context.getString(sideEffect.messageResId)
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
				}
			}
		}
	}

	if (uiState.isLoading) {
		// TODO 로딩
	} else {
		EpisodeDetailScreen(state = uiState)
	}
}

@Composable
internal fun EpisodeDetailScreen(
	state: EpisodeDetailState,
	modifier: Modifier = Modifier,
) {
	Timber.e(state.episode.toString())
	Timber.e(state.author.toString())
}
