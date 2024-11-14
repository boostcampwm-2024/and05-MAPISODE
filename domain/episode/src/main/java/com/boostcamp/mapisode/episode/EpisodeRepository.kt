package com.boostcamp.mapisode.episode

interface EpisodeRepository {
	suspend fun getEpisodesByGroup(groupId: String): List<EpisodeDTO>
	suspend fun getEpisodesByGroupAndLocation(
		groupId: String,
		start: Pair<Double, Double>,
		end: Pair<Double, Double>,
	): List<EpisodeDTO>

	suspend fun getEpisodesByGroupAndCategory(
		groupId: String,
		category: String,
	): List<EpisodeDTO>

	suspend fun getEpisodeById(episodeId: String): EpisodeDTO?

	suspend fun createEpisode(episodeDTO: EpisodeDTO): String
}
