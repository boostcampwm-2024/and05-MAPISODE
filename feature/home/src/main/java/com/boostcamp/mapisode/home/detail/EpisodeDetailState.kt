package com.boostcamp.mapisode.home.detail

import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.model.UserModel
import com.boostcamp.mapisode.ui.base.UiState

data class EpisodeDetailState(
	val isLoading: Boolean = true,
	val episode: EpisodeModel? = null,
	val author: UserModel? = null,
) : UiState
