package com.boostcamp.mapisode.navigation

import kotlinx.serialization.Serializable

sealed interface GroupRoute : Route {
	@Serializable
	data object Join : GroupRoute

	@Serializable
	data object Detail : GroupRoute

	@Serializable
	data object Creation : GroupRoute

	@Serializable
	data object Edit : GroupRoute
}
