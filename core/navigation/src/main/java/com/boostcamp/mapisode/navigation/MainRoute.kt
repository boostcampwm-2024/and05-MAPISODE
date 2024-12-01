package com.boostcamp.mapisode.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRoute : Route {
	@Serializable
	data object Home : MainRoute

	@Serializable
	data class Episode(val lat: Double? = null, val lng: Double? = null) : MainRoute

	@Serializable
	data object Group : MainRoute

	@Serializable
	data object Mypage : MainRoute
}
