package com.boostcamp.mapisode.login.di

import com.boostcamp.mapisode.auth.AuthRepository
import com.boostcamp.mapisode.auth.SignInWithGoogleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureAuthModule {
	@Provides
	@Singleton
	fun provideSignInWithGoogleUseCase(
		authRepository: AuthRepository,
	): SignInWithGoogleUseCase = SignInWithGoogleUseCase(authRepository)
}
