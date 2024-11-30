package com.boostcamp.mapisode.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute : Route {
	@Serializable
	data class Detail(val episodeId: String) : HomeRoute

	@Serializable
	data class List(val groupId: String) : HomeRoute

	@Serializable
	data class Edit(val episodeId: String) : HomeRoute
}
