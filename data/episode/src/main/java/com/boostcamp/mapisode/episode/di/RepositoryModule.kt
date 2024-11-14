package com.boostcamp.mapisode.episode.di

import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.episode.EpisodeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun bindEpisodeRepository(
		episodeRepositoryImpl: EpisodeRepositoryImpl,
	): EpisodeRepository
}
