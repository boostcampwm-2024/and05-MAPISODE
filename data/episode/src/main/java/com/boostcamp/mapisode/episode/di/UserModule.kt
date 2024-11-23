package com.boostcamp.mapisode.episode.di

import com.boostcamp.mapisode.episode.UserRepositoryImpl
import com.boostcamp.mapisode.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
	@Binds
	abstract fun bindEpisodeRepository(
		userRepositoryImpl: UserRepositoryImpl,
	): UserRepository
}
