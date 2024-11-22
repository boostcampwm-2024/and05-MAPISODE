package com.boostcamp.mapisode.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
	@Provides
	@Singleton
	fun providePreferencesDataStore(
		@ApplicationContext context: Context,
	): DataStore<Preferences> = PreferenceDataStoreFactory.create(
		produceFile = { context.preferencesDataStoreFile("user_preferences") },
	)

	@Provides
	@Singleton
	fun providePreferenceStorage(
		dataStore: DataStore<Preferences>,
	): UserPreferenceDataStore = UserPreferencesDataStoreImpl(dataStore)
}