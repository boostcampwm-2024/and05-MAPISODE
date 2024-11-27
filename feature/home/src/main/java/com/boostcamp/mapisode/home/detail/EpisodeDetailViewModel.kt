package com.boostcamp.mapisode.home.detail

import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class EpisodeDetailViewModel @Inject constructor(
) : BaseViewModel<EpisodeDetailIntent, EpisodeDetailState, EpisodeDetailSideEffect>(
	EpisodeDetailState(),
) {
	override fun onIntent(intent: EpisodeDetailIntent) {
		when (intent) {
			is EpisodeDetailIntent.LoadEpisodeDetail -> {
				loadEpisodeDetail(intent.episodeId)
			}
		}
	}

	private fun loadEpisodeDetail(episodeId: String) {

	}
}
