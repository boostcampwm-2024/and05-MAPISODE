package com.boostcamp.mapisode.episode.intent

import android.net.Uri
import com.boostcamp.mapisode.ui.base.UiIntent
import com.naver.maps.geometry.LatLng
import kotlinx.collections.immutable.PersistentList
import java.util.Date

sealed class NewEpisodeIntent : UiIntent {
	data object LoadMyGroups : NewEpisodeIntent()
	data class SetEpisodePics(val pics: PersistentList<Uri>) : NewEpisodeIntent()
	data class SetEpisodeLocation(val latLng: LatLng) : NewEpisodeIntent()
	data class SetIsCameraMoving(val isCameraMoving: Boolean) : NewEpisodeIntent()
	data class SetEpisodeAddress(val latLng: LatLng) : NewEpisodeIntent()
	data class SetEpisodeGroup(val group: String) : NewEpisodeIntent()
	data class SetEpisodeCategory(val category: EpisodeCategory) : NewEpisodeIntent()
	data class SetEpisodeTags(val tags: List<String>) : NewEpisodeIntent()
	data class SetEpisodeDate(val date: Date) : NewEpisodeIntent()
	data class SetEpisodeInfo(val episodeInfo: NewEpisodeInfo) : NewEpisodeIntent()
	data class SetEpisodeContent(val episodeContent: NewEpisodeContent) : NewEpisodeIntent()
	data class SetIsCreatingEpisode(val isCreatingEpisode: Boolean) : NewEpisodeIntent()
	data object CreateNewEpisode : NewEpisodeIntent()
	data object ClearEpisode : NewEpisodeIntent()
}
