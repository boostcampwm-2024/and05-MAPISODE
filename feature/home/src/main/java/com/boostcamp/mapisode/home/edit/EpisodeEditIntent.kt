package com.boostcamp.mapisode.home.edit

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.UiIntent

@Immutable
sealed class EpisodeEditIntent : UiIntent {
	data class LoadEpisode(val episodeId: String) : EpisodeEditIntent()
	data class OnEpisodeEditClick(
		val imageUrlList: List<String>,
		val location: String,
	) : EpisodeEditIntent()
	data class OnBackClick(val groupId: String) : EpisodeEditIntent()
}
