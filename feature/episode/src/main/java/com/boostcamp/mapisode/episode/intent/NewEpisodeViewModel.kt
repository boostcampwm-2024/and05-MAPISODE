package com.boostcamp.mapisode.episode.intent

import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.MAP_DEFAULT_ZOOM
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.naver.maps.map.CameraPosition
import timber.log.Timber

class NewEpisodeViewModel
	: BaseViewModel<NewEpisodeState, NewEpisodeSideEffect>(NewEpisodeState()) {

	fun onIntent(intent: NewEpisodeIntent) {
		when (intent) {
			is NewEpisodeIntent.SetEpisodeLocation -> {
				intent {
					copy(
						cameraPosition = CameraPosition(intent.latLng, MAP_DEFAULT_ZOOM),
					)
				}
			}

			is NewEpisodeIntent.SetEpisodeInfo -> {
				intent {
					copy(
						episodeInfo = intent.episodeInfo,
					)
				}
			}

			is NewEpisodeIntent.SetEpisodeGroup -> {
				intent {
					copy(
						episodeInfo = episodeInfo.copy(group = intent.group),
					)
				}
			}

			is NewEpisodeIntent.SetEpisodeCategory -> {
				intent {
					copy(
						episodeInfo = episodeInfo.copy(category = intent.category),
					)
				}
			}

			is NewEpisodeIntent.SetEpisodeTags -> {
				intent {
					copy(
						episodeInfo = episodeInfo.copy(tags = intent.tags),
					)
				}
			}

			is NewEpisodeIntent.SetEpisodeDate -> {
				intent {
					copy(
						episodeInfo = episodeInfo.copy(date = intent.date),
					)
				}
			}
		}
		Timber.d(uiState.value.toString())
	}
}
