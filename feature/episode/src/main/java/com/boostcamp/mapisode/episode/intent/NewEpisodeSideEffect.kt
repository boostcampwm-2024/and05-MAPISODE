package com.boostcamp.mapisode.episode.intent

import com.boostcamp.mapisode.ui.base.SideEffect

sealed class NewEpisodeSideEffect : SideEffect {
	data class ShowToast(val messageResId: Int) : NewEpisodeSideEffect()
}
