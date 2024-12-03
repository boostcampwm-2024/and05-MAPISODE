package com.boostcamp.mapisode.home.detail

import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.model.UserModel
import com.boostcamp.mapisode.ui.base.UiState

data class EpisodeDetailState(
	val isLoading: Boolean = false,
	val episode: EpisodeModel = EpisodeModel(),
	val author: UserModel? = null,
) : UiState
