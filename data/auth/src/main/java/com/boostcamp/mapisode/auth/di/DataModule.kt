package com.boostcamp.mapisode.auth.di

import android.content.Context
import com.boostcamp.mapisode.auth.AuthRepository
import com.boostcamp.mapisode.auth.AuthRepositoryImpl
import com.boostcamp.mapisode.auth.FirebaseAuthDataSource
import com.boostcamp.mapisode.auth.FirebaseAuthDataSourceImpl
import com.boostcamp.mapisode.auth.GoogleAuthDataSource
import com.boostcamp.mapisode.auth.GoogleAuthDataSourceImpl
import com.boostcamp.mapisode.auth.UserMapper
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
	@Provides
	@Singleton
	fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

	@Provides
	@Singleton
	fun provideGoogleAuthDataSource(
		@ApplicationContext context: Context,
	): GoogleAuthDataSource = GoogleAuthDataSourceImpl(context)

	@Provides
	@Singleton
	fun provideFirebaseAuthDataSource(
		firebaseAuth: FirebaseAuth,
	): FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)

	@Provides
	@Singleton
	fun provideUserMapper(): UserMapper = UserMapper()

	@Provides
	@Singleton
	fun provideAuthRepository(
		googleAuthDataSource: GoogleAuthDataSource,
		firebaseAuthDataSource: FirebaseAuthDataSource,
		userMapper: UserMapper,
	): AuthRepository = AuthRepositoryImpl(
		googleAuthDataSource,
		firebaseAuthDataSource,
		userMapper,
	)
}
