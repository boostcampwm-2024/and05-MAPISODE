package com.boostcamp.mapisode.mygroup.di

import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.mygroup.GroupRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GroupRepositoryModule {
	@Binds
	abstract fun bindGroupRepository(
		groupRepositoryImpl: GroupRepositoryImpl,
	): GroupRepository
}
