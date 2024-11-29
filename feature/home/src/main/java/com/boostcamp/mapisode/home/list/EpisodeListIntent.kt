package com.boostcamp.mapisode.home.list

import com.boostcamp.mapisode.ui.base.UiIntent

sealed class EpisodeListIntent : UiIntent {
	data class LoadEpisodeList(val groupId: String) : EpisodeListIntent()
}
