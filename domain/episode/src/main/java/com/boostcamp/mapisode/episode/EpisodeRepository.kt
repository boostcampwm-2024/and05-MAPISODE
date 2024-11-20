package com.boostcamp.mapisode.episode

import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.model.EpisodeModel

interface EpisodeRepository {
	suspend fun getEpisodesByGroup(groupId: String): List<EpisodeModel>
	suspend fun getEpisodesByGroupAndLocation(
		groupId: String,
		start: EpisodeLatLng,
		end: EpisodeLatLng,
		category: String? = null,
	): List<EpisodeModel>

	suspend fun getEpisodesByGroupAndCategory(
		groupId: String,
		category: String,
	): List<EpisodeModel>

	suspend fun getEpisodeById(episodeId: String): EpisodeModel?

	suspend fun createEpisode(episodeModel: EpisodeModel): String
}
