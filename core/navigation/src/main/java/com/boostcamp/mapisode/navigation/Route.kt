package com.boostcamp.mapisode.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
	@Serializable
	data object Auth : Route
}
