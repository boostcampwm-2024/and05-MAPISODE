package com.boostcamp.mapisode.home.story

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

	StoryViewer(
		imageUrls = uiState.episode.imageUrls.toPersistentList(),
		authorName = uiState.author?.name ?: "UNKNOWN",
		authorProfileUrl = uiState.author?.profileUrl ?: "",
		onClose = onBackClick,
	)
}
