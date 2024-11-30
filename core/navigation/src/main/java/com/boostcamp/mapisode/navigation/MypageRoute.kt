package com.boostcamp.mapisode.navigation

import kotlinx.serialization.Serializable

sealed interface MypageRoute : Route {
	@Serializable
	data object ProfileEdit : MypageRoute
}
