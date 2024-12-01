package com.boostcamp.mapisode.home.list

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.home.R
import com.boostcamp.mapisode.home.common.SortOption
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
	private val episodeRepository: EpisodeRepository,
	private val groupRepository: GroupRepository,
) :
	BaseViewModel<EpisodeListIntent, EpisodeListState, EpisodeListSideEffect>(EpisodeListState()) {

	private val userNameCache = ConcurrentHashMap<String, String>()

	override fun onIntent(intent: EpisodeListIntent) {
		when (intent) {
			is EpisodeListIntent.LoadInitialData -> loadGroupName(intent.groupId)
			is EpisodeListIntent.LoadEpisodeList -> loadEpisodeList(intent.groupId)
			is EpisodeListIntent.ChangeSortOption -> changeSortOption(intent.sortOption)
		}
	}

	private fun loadGroupName(groupId: String) {
		viewModelScope.launch {
			try {
				val groupName = groupRepository.getGroupByGroupId(groupId).name
				intent { copy(groupName = groupName) }
			} catch (e: Exception) {
				postSideEffect(EpisodeListSideEffect.ShowToast(R.string.episode_detail_load_error))
			}
		}
	}

	private fun loadEpisodeList(groupId: String) {
		viewModelScope.launch {
			try {
				val episodes = episodeRepository.getEpisodesByGroup(groupId).toPersistentList()
				val creatorIds = episodes.map { it.createdBy }.distinct()

				val newCreatorIds = creatorIds.filter { !userNameCache.containsKey(it) }

				val deferred = newCreatorIds.map { creatorId ->
					async {
						try {
							val userInfo = groupRepository.getUserInfoByUserId(creatorId)
							userNameCache[creatorId] = userInfo.name
						} catch (e: Exception) {
							userNameCache[creatorId] = "UNKNOWN"
						}
					}
				}

				deferred.awaitAll()

				val episodesWithCreatorName = episodes.map { episode ->
					val creatorName = userNameCache[episode.createdBy] ?: "UNKNOWN"
					episode.copy(createdByName = creatorName)
				}.toPersistentList()

				intent { copy(episodes = episodesWithCreatorName, isLoading = false) }
			} catch (e: Exception) {
				postSideEffect(EpisodeListSideEffect.ShowToast(R.string.episode_detail_load_error))
				intent { copy(isLoading = false) }
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
