package com.boostcamp.mapisode.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataStoreImpl @Inject constructor(
	private val dataStore: DataStore<Preferences>,
) : UserPreferenceDataStore {

	override fun getUserPreferencesFlow(): Flow<UserPreferences> = dataStore.data
		.catch { exception ->
			if (exception is IOException) {
				emit(emptyPreferences())
			} else {
				throw exception
			}
		}
		.map { preferences ->
			UserPreferences(
				userId = preferences[PreferenceKeys.USER_ID],
				username = preferences[PreferenceKeys.USERNAME],
				isFirstLaunch = preferences[PreferenceKeys.IS_FIRST_LAUNCH] ?: true,
				isLoggedIn = preferences[PreferenceKeys.IS_LOGGED_IN] ?: false,
				profileUrl = preferences[PreferenceKeys.PROFILE_URL],
				credentialIDToken = preferences[PreferenceKeys.CREDENTIAL_ID_TOKEN],
				recentSelectedGroup = preferences[PreferenceKeys.RECENT_SELECTED_GROUP],
			)
		}

	override fun getUserId(): Flow<String?> = dataStore.data.map { preferences ->
		preferences[PreferenceKeys.USER_ID]
	}

	override suspend fun checkLoggedIn(): Boolean = dataStore.data.map { preferences ->
		preferences[PreferenceKeys.IS_LOGGED_IN] ?: false
	}.first()

	override suspend fun updateUserId(userId: String) {
		if (userId.isBlank()) throw IllegalArgumentException("User ID cannot be empty")
		dataStore.edit { preferences ->
			preferences[PreferenceKeys.USER_ID] = userId
		}
	}

	override suspend fun updateUsername(username: String) {
		if (username.isBlank()) throw IllegalArgumentException("Username cannot be empty")
		dataStore.edit { preferences ->
			preferences[PreferenceKeys.USERNAME] = username
		}
	}

	override suspend fun updateIsFirstLaunch() {
		dataStore.edit { preferences ->
			preferences[PreferenceKeys.IS_FIRST_LAUNCH] = false
		}
	}

	override suspend fun updateIsLoggedIn(isLoggedIn: Boolean) {
		dataStore.edit { preferences ->
			preferences[PreferenceKeys.IS_LOGGED_IN] = isLoggedIn
		}
	}

	override suspend fun updateProfileUrl(profileUrl: String) {
//		커스텀 포토피커 도입 후 주석 삭제하겠습니다.
//		if (profileUrl.isEmpty()) throw IllegalArgumentException("Profile URL cannot be empty")
		dataStore.edit { preferences ->
			preferences[PreferenceKeys.PROFILE_URL] = profileUrl
		}
	}

	override suspend fun updateCredentialIdToken(idToken: String) {
		if (idToken.isBlank()) throw IllegalArgumentException("Credential ID Token cannot be empty")
		dataStore.edit { preferences ->
			preferences[PreferenceKeys.CREDENTIAL_ID_TOKEN] = idToken
		}
	}

	override suspend fun updateRecentSelectedGroup(group: String) {
		dataStore.edit { preferences ->
			preferences[PreferenceKeys.RECENT_SELECTED_GROUP] = group
		}
	}

	override suspend fun clearUserData() {
		dataStore.edit { preferences ->
			preferences.clear()
		}
	}
}
