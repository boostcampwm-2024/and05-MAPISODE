package com.boostcamp.mapisode.navigation

import kotlinx.serialization.Serializable

sealed interface NewEpisodeRoute : Route {
	@Serializable
	data object PickPhoto : NewEpisodeRoute

	@Serializable
	data object WriteInfo : NewEpisodeRoute

	@Serializable
	data object PickLocation : NewEpisodeRoute

	@Serializable
	data object WriteContent : NewEpisodeRoute
}
