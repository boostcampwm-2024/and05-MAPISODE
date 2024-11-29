package com.boostcamp.mapisode.home.list

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.home.R
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(private val episodeRepository: EpisodeRepository) :
	BaseViewModel<EpisodeListIntent, EpisodeListState, EpisodeListSideEffect>(EpisodeListState()) {
	override fun onIntent(intent: EpisodeListIntent) {
		when (intent) {
			is EpisodeListIntent.LoadEpisodeList -> loadEpisodeList(intent.groupId)
		}
	}

	private fun loadEpisodeList(groupId: String) {
		viewModelScope.launch {
			try {
				val episodes = episodeRepository.getEpisodesByGroup(groupId).toPersistentList()
				intent { copy(episodes = episodes) }
			} catch (e: Exception) {
				postSideEffect(EpisodeListSideEffect.ShowToast(R.string.episode_detail_load_error))
			}
		}
	}
}
