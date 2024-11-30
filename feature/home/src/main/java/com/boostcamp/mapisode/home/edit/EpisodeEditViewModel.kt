package com.boostcamp.mapisode.home.edit

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.common.util.toEpisodeLatLng
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.home.R
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.network.repository.NaverMapsRepository
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeEditViewModel @Inject constructor(
	private val episodeRepository: EpisodeRepository,
	private val naverMapsRepository: NaverMapsRepository,
) : BaseViewModel<EpisodeEditIntent, EpisodeEditState, EpisodeEditSideEffect>(
	EpisodeEditState(),
) {
	override fun onIntent(intent: EpisodeEditIntent) {
		when (intent) {
			is EpisodeEditIntent.LoadEpisode -> {
				loadEpisode(intent.episodeId)
			}

			is EpisodeEditIntent.OnEpisodeEditClick -> {
				editEpisode()
			}

			is EpisodeEditIntent.OnPictureClick -> {
				intent {
					copy(
						isSelectingPicture = true,
					)
				}
			}

			is EpisodeEditIntent.OnLocationClick -> {
				intent {
					copy(
						isSelectingLocation = true,
						episode = episode.copy(
							location = intent.latLng,
						),
					)
				}
			}

			is EpisodeEditIntent.OnSetLocation -> {
				getAddress(intent.latLng)
				intent {
					copy(
						episode = episode.copy(
						),
					)
				}
			}

			is EpisodeEditIntent.OnFinishLocationSelection -> {
				intent {
					copy(
						isSelectingLocation = false,
					)
				}
			}

			is EpisodeEditIntent.OnSetPictures -> {
				intent {
					copy(
						isSelectingPicture = false,
						episode = episode.copy(
							localImageUrl = intent.imageUrlList.toPersistentList(),
						),
					)
				}
			}

			is EpisodeEditIntent.OnEditClick -> {

			}

			is EpisodeEditIntent.OnBackClick -> {
				postSideEffect(EpisodeEditSideEffect.NavigateToEpisodeDetailScreen)
			}
		}
	}

	private fun loadEpisode(episodeId: String) {
		viewModelScope.launch {
			try {
				val episode = episodeRepository.getEpisodeById(episodeId) ?: throw Exception()
				intent {
					copy(
						isInitializing = false,
						episode = episode.toEpisodeEditInfo(),
					)
				}
			} catch (e: Exception) {
				postSideEffect(
					EpisodeEditSideEffect.ShowToast(
						R.string.error_episode_not_loaded,
					),
				)
			}
		}
	}

	private fun getAddress(latLng: LatLng) {
		viewModelScope.launch {
			try {
				val coord = "${latLng.longitude},${latLng.latitude}"
				val address = naverMapsRepository.reverseGeoCode(coord).getOrDefault("")
				intent {
					copy(
						episode = episode.copy(
							address = address,
							location = latLng.toEpisodeLatLng(),
						),
					)
				}
			} catch (e: Exception) {
				postSideEffect(
					EpisodeEditSideEffect.ShowToast(1),
				)
			}
		}
	}

	private fun editEpisode() {
		try {
			viewModelScope.launch {
				episodeRepository.updateEpisode(EpisodeModel())
				postSideEffect(EpisodeEditSideEffect.NavigateToEpisodeDetailScreen)
			}
		} catch (e: Exception) {
			postSideEffect(
				EpisodeEditSideEffect.ShowToast(
					R.string.error_episode_not_edited,
				),
			)
		}
	}
}
