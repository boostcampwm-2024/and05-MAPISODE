package com.boostcamp.mapisode.storage.di

import com.boostcamp.mapisode.storage.StorageRepository
import com.boostcamp.mapisode.storage.StorageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
	@Binds
	abstract fun bindStorageRepository(
		userRepositoryImpl: StorageRepositoryImpl,
	): StorageRepository
}
