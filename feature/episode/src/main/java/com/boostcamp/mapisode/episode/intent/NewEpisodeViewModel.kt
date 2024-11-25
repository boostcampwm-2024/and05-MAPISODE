package com.boostcamp.mapisode.episode.intent

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.MAP_DEFAULT_ZOOM
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.naver.maps.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewEpisodeViewModel @Inject constructor(private val episodeRepository: EpisodeRepository) :
	BaseViewModel<NewEpisodeIntent, NewEpisodeState, NewEpisodeSideEffect>(NewEpisodeState()) {

	override fun onIntent(intent: NewEpisodeIntent) {
		when (intent) {
			is NewEpisodeIntent.SetEpisodeLocation -> {
				intent {
					copy(
						cameraPosition = CameraPosition(intent.latLng, MAP_DEFAULT_ZOOM),
						episodeInfo = episodeInfo.copy(location = intent.latLng),
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

			is NewEpisodeIntent.SetEpisodeContent -> {
				intent {
					copy(
						episodeContent = intent.episodeContent,
					)
				}
			}

			is NewEpisodeIntent.CreateNewEpisode -> {
				intent {
					val episodeModel = this.toDomainModel()
					viewModelScope.launch {
						episodeRepository.createEpisode(episodeModel)
					}
					copy(
						episodeInfo = NewEpisodeInfo(),
						episodeContent = NewEpisodeContent(),
					)
				}
			}
		}
		Timber.d(uiState.value.toString())
	}
}
