package com.boostcamp.mapisode.home.detail

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.home.R
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.boostcamp.mapisode.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
	private val episodeRepository: EpisodeRepository,
	private val userRepository: UserRepository,
) : BaseViewModel<EpisodeDetailIntent, EpisodeDetailState, EpisodeDetailSideEffect>(
	EpisodeDetailState(),
) {
	override fun onIntent(intent: EpisodeDetailIntent) {
		when (intent) {
			is EpisodeDetailIntent.LoadEpisodeDetail -> loadEpisodeDetail(intent.episodeId)
			is EpisodeDetailIntent.OpenStoryViewer -> postSideEffect(EpisodeDetailSideEffect.OpenStoryViewer)
		}
	}

	private fun loadEpisodeDetail(episodeId: String) {
		viewModelScope.launch {
			intent { copy(isLoading = true) }
			try {
				val episode = episodeRepository.getEpisodeById(episodeId)
				if (episode != null) {
					val author = userRepository.getUserInfo(episode.createdBy)
					intent {
						copy(
							isLoading = false,
							episode = episode,
							author = author,
						)
					}
				} else {
					intent { copy(isLoading = false) }
					postSideEffect(EpisodeDetailSideEffect.ShowToast(R.string.episode_detail_not_found_error))
				}
			} catch (e: Exception) {
				intent { copy(isLoading = false) }
				postSideEffect(EpisodeDetailSideEffect.ShowToast(R.string.episode_detail_load_error))
			}
		}
	}
}
