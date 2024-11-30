package com.boostcamp.mapisode.home.list

import com.boostcamp.mapisode.home.common.SortOption
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class EpisodeListState(
	val isLoading: Boolean = true,
	val episodes: PersistentList<EpisodeModel> = persistentListOf(),
	val selectedSortOption: SortOption = SortOption.LATEST,
) : UiState
