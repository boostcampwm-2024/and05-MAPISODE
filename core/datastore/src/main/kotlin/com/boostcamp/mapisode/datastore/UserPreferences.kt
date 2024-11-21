package com.boostcamp.mapisode.datastore

data class UserPreferences(
	val userId: String?,
	val username: String?,
	val isFirstLaunch: Boolean = true,
	val isLoggedIn: Boolean = false,
	val profileUrl: String?,
	val authToken: String?,
	val recentSelectedGroup: String?,
)
