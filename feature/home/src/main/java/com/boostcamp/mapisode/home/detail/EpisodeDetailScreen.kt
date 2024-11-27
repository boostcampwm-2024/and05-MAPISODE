package com.boostcamp.mapisode.home.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun EpisodeDetailRoute(
	episodeId: String,
	viewModel: EpisodeDetailViewModel = hiltViewModel(),
) {
	LaunchedEffect(Unit) {
		viewModel.onIntent(EpisodeDetailIntent.LoadEpisodeDetail(episodeId))
	}
}
