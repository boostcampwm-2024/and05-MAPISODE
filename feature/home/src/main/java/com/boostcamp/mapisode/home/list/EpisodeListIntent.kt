package com.boostcamp.mapisode.home.list

import com.boostcamp.mapisode.home.common.SortOption
import com.boostcamp.mapisode.ui.base.UiIntent

sealed class EpisodeListIntent : UiIntent {
	data class LoadInitialData(val groupId: String) : EpisodeListIntent()
	data class LoadEpisodeList(val groupId: String) : EpisodeListIntent()
	data class ChangeSortOption(val sortOption: SortOption) : EpisodeListIntent()
}
