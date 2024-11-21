package com.boostcamp.mapisode.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceDataStoreImpl @Inject constructor(
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
				authToken = preferences[PreferenceKeys.AUTH_TOKEN],
				recentSelectedGroup = preferences[PreferenceKeys.RECENT_SELECTED_CATEGORY],
			)
		}

	override suspend fun updateUserId(userId: String) {
		dataStore.edit { preferences ->
			preferences[PreferenceKeys.USER_ID] = userId
		}
	}

	override suspend fun updateUsername(username: String) {
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
		dataStore.edit { preferences ->
			preferences[PreferenceKeys.PROFILE_URL] = profileUrl
		}
	}

	override suspend fun updateAuthToken(authToken: String) {
		dataStore.edit { preferences ->
			preferences[PreferenceKeys.AUTH_TOKEN] = authToken
		}
	}

	override suspend fun updateRecentSelectedGroup(group: String) {
		dataStore.edit { preferences ->
			preferences[PreferenceKeys.RECENT_SELECTED_CATEGORY] = group
		}
	}

	override suspend fun clearUserData() {
		dataStore.edit { preferences ->
			preferences.remove(PreferenceKeys.USER_ID)
			preferences.remove(PreferenceKeys.USERNAME)
			preferences.remove(PreferenceKeys.IS_LOGGED_IN)
			preferences.remove(PreferenceKeys.PROFILE_URL)
			preferences.remove(PreferenceKeys.AUTH_TOKEN)
			preferences.remove(PreferenceKeys.RECENT_SELECTED_CATEGORY)
		}
	}
}
