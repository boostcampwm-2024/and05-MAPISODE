package com.boostcamp.mapisode.home.edit

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.home.R
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeEditViewModel @Inject constructor(
	private val episodeRepository: EpisodeRepository,
) : BaseViewModel<EpisodeEditIntent, EpisodeEditState, EpisodeEditSideEffect>(
	EpisodeEditState(),
) {
	override fun onIntent(intent: EpisodeEditIntent) {
		when (intent) {
			is EpisodeEditIntent.LoadEpisode -> {
				loadEpisode(intent.episodeId)
			}

			is EpisodeEditIntent.OnEpisodeEditClick -> {
				editEpisode()
			}

			is EpisodeEditIntent.OnBackClick -> {
				postSideEffect(EpisodeEditSideEffect.NavigateToGroupEpisodeDetailScreen)
			}
		}
	}

	private fun loadEpisode(episodeId: String) {
		viewModelScope.launch {
			try {
				val episode = episodeRepository.getEpisodeById(episodeId) ?: throw Exception()
				intent {
					copy(
						isInitializing = false,
						episode = episode,
					)
				}
			} catch (e: Exception) {
				postSideEffect(
					EpisodeEditSideEffect.ShowToast(
						R.string.error_episode_not_loaded,
					),
				)
			}
		}
	}

	private fun editEpisode() {
		try {
			viewModelScope.launch {
				episodeRepository.updateEpisode(EpisodeModel())
				postSideEffect(EpisodeEditSideEffect.NavigateToGroupEpisodeDetailScreen)
			}
		} catch (e: Exception) {
			postSideEffect(
				EpisodeEditSideEffect.ShowToast(
					R.string.error_episode_not_edited,
				),
			)
		}
	}
}
