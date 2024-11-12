package com.boostcamp.mapisode.home

import com.boostcamp.mapisode.ui.base.SideEffect

sealed class HomeSideEffect : SideEffect {
	data class ShowToast(val message: String) : HomeSideEffect()
}
