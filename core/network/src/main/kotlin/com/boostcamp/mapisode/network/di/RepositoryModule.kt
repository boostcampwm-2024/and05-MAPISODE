package com.boostcamp.mapisode.network.di

import com.boostcamp.mapisode.network.repository.NaverMapsRepository
import com.boostcamp.mapisode.network.repository.NaverMapsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun bindNaverMapsRepository(
		naverMapsRepositoryImpl: NaverMapsRepositoryImpl,
	): NaverMapsRepository
}
