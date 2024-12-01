package com.boostcamp.mapisode.episode

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.MAX_PHOTO_COUNTS
import com.boostcamp.mapisode.episode.intent.NewEpisodeIntent
import com.boostcamp.mapisode.episode.intent.NewEpisodeSideEffect
import com.boostcamp.mapisode.episode.intent.NewEpisodeViewModel
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.ui.photopicker.MapisodePhotoPicker
import com.naver.maps.geometry.LatLng
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun NewEpisodePicsScreen(
	initialLatLng: EpisodeLatLng? = null,
	viewModel: NewEpisodeViewModel = hiltViewModel(),
	onNavigateToInfo: () -> Unit,
	onBackPressed: () -> Unit,
) {
	val context = LocalContext.current
	initialLatLng?.let { latLng ->
		val mapsLatLng = LatLng(latLng.latitude, latLng.longitude)
		viewModel.onIntent(NewEpisodeIntent.SetEpisodeLocation(mapsLatLng))
		viewModel.onIntent(NewEpisodeIntent.SetEpisodeAddress(mapsLatLng))
	}

	LaunchedEffect(Unit) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is NewEpisodeSideEffect.ShowToast -> {
					Toast.makeText(
						context,
						sideEffect.messageResId,
						Toast.LENGTH_LONG,
					).show()
				}

				else -> {}
			}
		}
	}

	MapisodePhotoPicker(
		modifier = Modifier
			.fillMaxSize(),
		numOfPhoto = MAX_PHOTO_COUNTS,
		onPhotoSelected = { photoInfos ->
			viewModel.onIntent(
				NewEpisodeIntent.SetEpisodePics(
					photoInfos
						.map { Uri.parse(it.uri) }
						.toPersistentList(),
				),
			)
			onNavigateToInfo()
		},
		onBackPressed = {
			onBackPressed()
		},
		onPermissionDenied = {
			Toast.makeText(
				context,
				R.string.new_episode_pictures_permission_denied,
				Toast.LENGTH_LONG,
			).show()
		},
	)
}
