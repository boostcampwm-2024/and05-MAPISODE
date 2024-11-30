package com.boostcamp.mapisode.home.edit

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.ui.base.UiState

@Immutable
data class EpisodeEditState(
	val isInitializing: Boolean = true,
	val isEpisodeEditError: Boolean = false,
	val episode: EpisodeModel? = null,
) : UiState
