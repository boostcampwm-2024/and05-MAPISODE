package com.boostcamp.mapisode.home.edit

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.common.util.toEpisodeLatLng
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.home.R
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.network.repository.NaverMapsRepository
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeEditViewModel @Inject constructor(
	private val userPreferenceDataStore: UserPreferenceDataStore,
	private val groupRepository: GroupRepository,
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

			is EpisodeEditIntent.OnPictureClick -> {
				intent {
					copy(
						isSelectingPicture = !isSelectingPicture,
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
			}

			is EpisodeEditIntent.OnRequestSelection -> {
				intent {
					copy(
						isSelectingLocation = false,
						episode = episode.copy(
							selectedAddress = intent.selectedAddress,
							searchedAddress = "",
						),
					)
				}
			}

			is EpisodeEditIntent.OnDismissSelection -> {
				intent {
					copy(
						isSelectingLocation = false,
						episode = episode.copy(
							searchedAddress = "",
						),
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
				intent {
					copy(
						isEditingInProgress = true,
					)
				}
				editEpisode(intent.newState)
			}

			is EpisodeEditIntent.OnBackClickToEditScreen -> {
				intent {
					copy(
						isSelectingLocation = false,
						isSelectingPicture = false,
					)
				}
			}

			is EpisodeEditIntent.OnBackClickToOutOfEditScreen -> {
				postSideEffect(EpisodeEditSideEffect.NavigateBackScreen)
			}
		}
	}

	private fun loadEpisode(episodeId: String) {
		viewModelScope.launch {
			try {
				val episode = episodeRepository.getEpisodeById(episodeId) ?: throw Exception()
				val userId = userPreferenceDataStore.getUserId().first() ?: throw Exception()
				val myGroups = groupRepository.getGroupsByUserId(userId)
					.map { groupModel ->
						GroupsId(name = groupModel.name, id = groupModel.id)
					}
				intent {
					copy(
						isInitializing = false,
						episode = episode.toEpisodeEditInfo().copy(
							id = episodeId,
							group = myGroups.first { it.id == episode.group }.name,
						),
						groups = myGroups.toPersistentList(),
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
							searchedAddress = address,
							location = latLng.toEpisodeLatLng(),
						),
					)
				}
			} catch (e: Exception) {
				postSideEffect(
					EpisodeEditSideEffect.ShowToast(R.string.error_address_not_loaded),
				)
			}
		}
	}

	private fun editEpisode(editedEpisode: EpisodeEditInfo) {
		try {
			viewModelScope.launch {
				episodeRepository.updateEpisode(editedEpisode.toDomainModel())
				intent { copy(isEditingInProgress = false) }
				postSideEffect(EpisodeEditSideEffect.NavigateBackScreen)
				postSideEffect(
					EpisodeEditSideEffect.ShowToast(
						R.string.success_episode_edit,
					),
				)
			}
		} catch (e: Exception) {
			intent { copy(isEditingInProgress = false) }
			postSideEffect(
				EpisodeEditSideEffect.ShowToast(
					R.string.error_episode_not_edited,
				),
			)
		}
	}
}
