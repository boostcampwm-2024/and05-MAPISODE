package com.boostcamp.mapisode.user.di

import com.boostcamp.mapisode.user.UserRepository
import com.boostcamp.mapisode.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
	@Binds
	abstract fun bindUserRepository(
		userRepositoryImpl: UserRepositoryImpl,
	): UserRepository
}
