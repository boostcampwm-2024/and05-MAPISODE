package com.boostcamp.mapisode.home.edit

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.ui.base.UiIntent
import com.naver.maps.geometry.LatLng
import kotlinx.collections.immutable.PersistentList

@Immutable
sealed class EpisodeEditIntent : UiIntent {
	data class LoadEpisode(val episodeId: String) : EpisodeEditIntent()
	data object OnPictureClick : EpisodeEditIntent()
	data class OnLocationClick(val latLng: EpisodeLatLng) : EpisodeEditIntent()
	data class OnSetLocation(val latLng: LatLng) : EpisodeEditIntent()
	data class OnRequestSelection(val selectedAddress: String) : EpisodeEditIntent()
	data object OnDismissSelection : EpisodeEditIntent()
	data class OnSetPictures(val imageUrlList: PersistentList<Uri>) : EpisodeEditIntent()
	data class OnEditClick(val newState: EpisodeEditInfo) : EpisodeEditIntent()
	data object OnBackClickToEditScreen : EpisodeEditIntent()
	data object OnBackClickToOutOfEditScreen : EpisodeEditIntent()
}
