package com.boostcamp.mapisode.home.list

import com.boostcamp.mapisode.ui.base.SideEffect

sealed class EpisodeListSideEffect : SideEffect {
	data class ShowToast(val messageResId: Int) : EpisodeListSideEffect()
}
