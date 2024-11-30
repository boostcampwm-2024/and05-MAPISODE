package com.boostcamp.mapisode.home.list

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.home.R
import com.boostcamp.mapisode.home.common.SortOption
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(private val episodeRepository: EpisodeRepository) :
	BaseViewModel<EpisodeListIntent, EpisodeListState, EpisodeListSideEffect>(EpisodeListState()) {
	override fun onIntent(intent: EpisodeListIntent) {
		when (intent) {
			is EpisodeListIntent.LoadEpisodeList -> loadEpisodeList(intent.groupId)
			is EpisodeListIntent.ChangeSortOption -> changeSortOption(intent.sortOption)
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

	private fun changeSortOption(sortOption: SortOption) {
		val sortedEpisodes = sortEpisodes(currentState.episodes, sortOption)
		intent {
			copy(
				selectedSortOption = sortOption,
				episodes = sortedEpisodes,
			)
		}
	}

	private fun sortEpisodes(
		episodes: List<EpisodeModel>,
		sortOption: SortOption,
	): PersistentList<EpisodeModel> {
		val sortedList = when (sortOption) {
			SortOption.LATEST -> episodes.sortedByDescending { it.createdAt }
			SortOption.OLDEST -> episodes.sortedBy { it.createdAt }
			SortOption.NAME -> episodes.sortedBy { it.title }
		}

		return sortedList.toPersistentList()
	}
}
