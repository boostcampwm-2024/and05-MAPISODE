package com.boostcamp.mapisode.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRoute : Route {
	@Serializable
	data object Home : MainRoute

	@Serializable
	data object Episode : MainRoute

	@Serializable
	data object Group : MainRoute

	@Serializable
	data object Mypage : MainRoute
}
