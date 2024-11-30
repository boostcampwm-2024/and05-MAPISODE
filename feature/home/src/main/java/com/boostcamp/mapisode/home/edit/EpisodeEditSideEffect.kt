package com.boostcamp.mapisode.home.edit

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.ui.base.SideEffect

@Immutable
sealed class EpisodeEditSideEffect : SideEffect {
	data class ShowToast(val messageResId: Int) : EpisodeEditSideEffect()
	data object NavigateToEpisodeDetailScreen : EpisodeEditSideEffect()
}
