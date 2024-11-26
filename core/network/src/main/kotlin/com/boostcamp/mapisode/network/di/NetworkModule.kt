package com.boostcamp.mapisode.network.di

import com.boostcamp.mapisode.network.repository.NaverMapsRepository
import com.boostcamp.mapisode.network.repository.NaverMapsRepositoryImpl
import com.boostcamp.mapisode.network.retrofit.NaverMapsApi
import com.boostcamp.mapisode.network.retrofit.NaverMapsApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	fun provideNaverMapsApi(): NaverMapsApi = NaverMapsApiClient.create()

	@Provides
	fun bindNaverMapsRepository(naverMapsApi: NaverMapsApi): NaverMapsRepository =
		NaverMapsRepositoryImpl(naverMapsApi)
}
