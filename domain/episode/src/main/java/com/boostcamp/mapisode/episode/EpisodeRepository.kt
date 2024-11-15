package com.boostcamp.mapisode.episode

interface EpisodeRepository {
	suspend fun getEpisodesByGroup(groupId: String): List<EpisodeModel>
	suspend fun getEpisodesByGroupAndLocation(
		groupId: String,
		start: Pair<Double, Double>,
		end: Pair<Double, Double>,
	): List<EpisodeModel>

	suspend fun getEpisodesByGroupAndCategory(
		groupId: String,
		category: String,
	): List<EpisodeModel>

	suspend fun getEpisodeById(episodeId: String): EpisodeModel?

	suspend fun createEpisode(episodeModel: EpisodeModel): String
}
