package com.boostcamp.mapisode.episode.intent

import com.naver.maps.geometry.LatLng
import java.util.Date

sealed class NewEpisodeIntent {
	data class SetEpisodeLocation(val latLng: LatLng) : NewEpisodeIntent()
	data class SetEpisodeGroup(val group: String) : NewEpisodeIntent()
	data class SetEpisodeCategory(val category: String) : NewEpisodeIntent()
	data class SetEpisodeTags(val tags: String) : NewEpisodeIntent()
	data class SetEpisodeDate(val date: Date) : NewEpisodeIntent()
	data class SetEpisodeInfo(val episodeInfo: NewEpisodeInfo) : NewEpisodeIntent()
}
