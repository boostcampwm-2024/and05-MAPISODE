package com.boostcamp.mapisode.episode.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
	@Provides
	fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

	@Provides
	fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage
}
