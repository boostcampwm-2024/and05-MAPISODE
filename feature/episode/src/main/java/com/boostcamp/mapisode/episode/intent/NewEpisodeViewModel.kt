package com.boostcamp.mapisode.episode.intent

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.MAP_DEFAULT_ZOOM
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.network.repository.NaverMapsRepository
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.naver.maps.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewEpisodeViewModel @Inject constructor(
	private val episodeRepository: EpisodeRepository,
	private val groupRepository: GroupRepository,
	private val naverMapsRepository: NaverMapsRepository,
	private val userPreferenceDataStore: UserPreferenceDataStore,
) : BaseViewModel<NewEpisodeIntent, NewEpisodeState, NewEpisodeSideEffect>(NewEpisodeState()) {

	override fun onIntent(intent: NewEpisodeIntent) {
		when (intent) {
			is NewEpisodeIntent.LoadMyGroups -> {
				viewModelScope.launch {
					try {
						val userId = userPreferenceDataStore.getUserId().firstOrNull() ?: ""
						val myGroups = groupRepository.getGroupsByUserId(userId)
							.map { groupModel ->
								GroupInfo(name = groupModel.name, groupId = groupModel.id)
							}
						intent {
							copy(
								myGroups = myGroups,
							)
						}
					} catch (e: Exception) {
						throw e
					}
				}
			}

			is NewEpisodeIntent.SetEpisodePics -> {
				intent {
					copy(
						episodeContent = episodeContent.copy(images = intent.pics),
					)
				}
			}

			is NewEpisodeIntent.SetIsCameraMoving -> {
				intent {
					copy(
						isCameraMoving = intent.isCameraMoving,
					)
				}
			}

			is NewEpisodeIntent.SetEpisodeAddress -> {
				viewModelScope.launch {
					val coord = "${intent.latLng.longitude},${intent.latLng.latitude}"
					val address = naverMapsRepository.reverseGeoCode(coord).getOrDefault("")
					intent {
						copy(
							episodeAddress = address,
						)
					}
				}
			}

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
				viewModelScope.launch {
					val userId = userPreferenceDataStore.getUserId().firstOrNull() ?: ""
					val episodeModel = uiState.value.toDomainModel(userId)
					episodeRepository.createEpisode(episodeModel)
					intent {
						copy(
							episodeInfo = NewEpisodeInfo(),
							episodeContent = NewEpisodeContent(),
						)
					}
				}
			}
		}
	}
}
