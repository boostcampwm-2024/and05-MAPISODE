package com.boostcamp.mapisode.home.detail

import com.boostcamp.mapisode.ui.base.SideEffect

sealed class EpisodeDetailSideEffect : SideEffect {
	data class ShowToast(val messageResId: Int) : EpisodeDetailSideEffect()
	data object OpenStoryViewer : EpisodeDetailSideEffect()
}
