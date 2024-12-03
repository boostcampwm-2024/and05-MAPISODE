package com.boostcamp.mapisode.home.detail

import com.boostcamp.mapisode.ui.base.UiIntent

sealed class EpisodeDetailIntent : UiIntent {
	data class LoadEpisodeDetail(val episodeId: String) : EpisodeDetailIntent()
	data object OpenStoryViewer : EpisodeDetailIntent()
}
