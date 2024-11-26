package com.boostcamp.mapisode.datastore

import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataStore {
	fun getUserPreferencesFlow(): Flow<UserPreferences>
	fun getUserId(): Flow<String?>
	suspend fun checkLoggedIn(): Boolean

	suspend fun updateUserId(userId: String)
	suspend fun updateUsername(username: String)
	suspend fun updateIsFirstLaunch()
	suspend fun updateIsLoggedIn(isLoggedIn: Boolean)
	suspend fun updateProfileUrl(profileUrl: String)
	suspend fun updateCredentialIdToken(idToken: String)
	suspend fun updateRecentSelectedGroup(group: String)

	suspend fun clearUserData()
}
