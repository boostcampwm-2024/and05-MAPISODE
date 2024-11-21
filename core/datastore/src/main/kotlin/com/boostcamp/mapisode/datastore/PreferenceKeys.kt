package com.boostcamp.mapisode.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

internal object PreferenceKeys {
	val USER_ID = stringPreferencesKey("user_id")
	val USERNAME = stringPreferencesKey("username")
	val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
	val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
	val PROFILE_URL = stringPreferencesKey("profile_url")
	val AUTH_TOKEN = stringPreferencesKey("auth_token")
	val RECENT_SELECTED_CATEGORY = stringPreferencesKey("recent_selected_category")
}
