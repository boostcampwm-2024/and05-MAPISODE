package com.boostcamp.mapisode.navigation

import kotlinx.serialization.Serializable

sealed interface GroupRoute : Route {
	@Serializable
	data object Join : GroupRoute

	@Serializable
	data class Detail(val groupId: String) : GroupRoute

	@Serializable
	data object Creation : GroupRoute

	@Serializable
	data class Edit(val groupId: String) : GroupRoute
}
