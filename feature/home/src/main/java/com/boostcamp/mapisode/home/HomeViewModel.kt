package com.boostcamp.mapisode.home

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.common.util.distanceTo
import com.boostcamp.mapisode.common.util.toEpisodeLatLng
import com.boostcamp.mapisode.datastore.UserPreferenceDataStore
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.home.common.ChipType
import com.boostcamp.mapisode.home.common.HomeConstant.DEFAULT_ZOOM
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.mygroup.GroupRepository
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val episodeRepository: EpisodeRepository,
	private val groupRepository: GroupRepository,
	private val userPreferenceDataStore: UserPreferenceDataStore,
) :
	BaseViewModel<HomeIntent, HomeState, HomeSideEffect>(HomeState()) {

	override fun onIntent(intent: HomeIntent) {
		when (intent) {
			is HomeIntent.LoadInitialData -> {
				loadInitialData()
				loadGroups()
			}

			is HomeIntent.LoadGroups -> {
				loadGroups()
			}

			is HomeIntent.RequestLocationPermission -> {
				// 위치 권한 요청이 아직 이루어지지 않은 경우에만 요청
				if (!currentState.hasRequestedPermission) {
					postSideEffect(HomeSideEffect.RequestLocationPermission)
					onIntent(HomeIntent.MarkPermissionRequested)
				}
			}

			is HomeIntent.SetInitialLocation -> {
				setInitialLocation(intent.latLng)
			}

			is HomeIntent.UpdateLocationPermission -> {
				updateLocationPermission(intent.isGranted)
				if (intent.isGranted) {
					postSideEffect(HomeSideEffect.SetInitialLocation)
				} else {
					postSideEffect(HomeSideEffect.ShowToast(R.string.home_location_permission_plz))
				}
			}

			is HomeIntent.MarkPermissionRequested -> {
				setHasRequestedPermission()
			}

			is HomeIntent.SelectChip -> {
				setSelectedChip(intent.chipType)
			}

			is HomeIntent.ShowBottomSheet -> {
				toggleBottomSheet()
			}

			is HomeIntent.LoadEpisode -> {
				loadEpisodes(intent.start, intent.end, intent.shouldSort)
			}

			is HomeIntent.ClickTextMarker -> {
				postSideEffect(HomeSideEffect.NavigateToEpisode(intent.latLng))
			}

			is HomeIntent.ShowCard -> {
				showCard(intent.selectedEpisode)
			}

			is HomeIntent.CloseCard -> {
				closeCard()
			}

			is HomeIntent.MapMovedWhileCardVisible -> {
				mapMovedWhileCardVisible()
			}

			is HomeIntent.StartProgrammaticCameraMove -> {
				setProgrammaticCameraMove(true)
			}

			is HomeIntent.EndProgrammaticCameraMove -> {
				setProgrammaticCameraMove(false)
			}

			is HomeIntent.NavigateToEpisode -> {
				postSideEffect(HomeSideEffect.NavigateToEpisodeDetail(intent.episodeId))
			}

			is HomeIntent.SelectGroup -> {
				selectGroup(intent.groupId)
			}
		}
	}

	private fun loadInitialData() {
		viewModelScope.launch {
			try {
				val cachedGroupId = userPreferenceDataStore.getRecentSelectedGroup().firstOrNull()
					?: userPreferenceDataStore.getUserId().firstOrNull() ?: throw Exception()

				intent {
					copy(selectedGroupId = cachedGroupId)
				}

				selectGroup(cachedGroupId)
			} catch (e: Exception) {
				postSideEffect(HomeSideEffect.ShowToast(R.string.error_load_episodes))
			}
		}
	}

	private fun setSelectedChip(chipType: ChipType) {
		intent {
			copy(selectedChip = if (currentState.selectedChip == chipType) null else chipType)
		}
	}

	private fun setHasRequestedPermission() {
		intent {
			copy(hasRequestedPermission = true)
		}
	}

	private fun setInitialLocation(latLng: LatLng) {
		intent {
			copy(
				cameraPosition = CameraPosition(latLng, DEFAULT_ZOOM),
				isInitialLocationSet = true,
			)
		}
	}

	private fun updateLocationPermission(isGranted: Boolean) {
		intent {
			copy(isLocationPermissionGranted = isGranted)
		}
	}

	private fun toggleBottomSheet() {
		intent {
			copy(isBottomSheetVisible = !isBottomSheetVisible)
		}
	}

	private fun loadEpisodes(
		start: EpisodeLatLng,
		end: EpisodeLatLng,
		shouldSort: Boolean = false,
	) {
		viewModelScope.launch {
			try {
				val groupId = currentState.selectedGroupId
					?: userPreferenceDataStore.getRecentSelectedGroup().firstOrNull()
					?: throw Exception("No group id available")
				val category = currentState.selectedChip?.name

				val episodes = episodeRepository.getEpisodesByGroupAndLocation(
					groupId = groupId,
					start = start,
					end = end,
					category = category,
				).toPersistentList()

				if (shouldSort) {
					val referenceLocation = currentState.selectedMarkerPosition?.toEpisodeLatLng()
						?: EpisodeLatLng(0.0, 0.0)
					val sortedEpisodes = sortEpisodesByDistance(episodes, referenceLocation)

					intent {
						copy(
							episodes = episodes,
							selectedEpisodes = sortedEpisodes.toPersistentList(),
							selectedEpisodeIndex = 0,
							isMapMovedWhileCardVisible = false,
							showRefreshButton = false,
						)
					}
				} else {
					intent {
						copy(
							episodes = episodes,
						)
					}
				}
			} catch (e: Exception) {
				postSideEffect(HomeSideEffect.ShowToast(R.string.error_load_episodes))
			}
		}
	}

	private fun showCard(selectedEpisode: EpisodeModel) {
		val sortedEpisodes = sortEpisodesByDistance(
			currentState.episodes,
			selectedEpisode.location,
		)

		val selectedIndex = sortedEpisodes.indexOfFirst { it.id == selectedEpisode.id }

		intent {
			copy(
				isCardVisible = true,
				selectedEpisodes = sortedEpisodes.toPersistentList(),
				selectedEpisodeIndex = if (selectedIndex >= 0) selectedIndex else 0,
				selectedMarkerPosition = LatLng(
					selectedEpisode.location.latitude,
					selectedEpisode.location.longitude,
				),
				isMapMovedWhileCardVisible = false,
				showRefreshButton = false,
			)
		}
	}

	private fun closeCard() {
		intent {
			copy(
				isCardVisible = false,
				selectedEpisodes = persistentListOf(),
				selectedEpisodeIndex = 0,
				selectedMarkerPosition = null,
				isMapMovedWhileCardVisible = false,
				showRefreshButton = false,
			)
		}
	}

	private fun mapMovedWhileCardVisible() {
		intent {
			copy(
				isMapMovedWhileCardVisible = true,
				showRefreshButton = true,
			)
		}
	}

	private fun sortEpisodesByDistance(
		episodes: List<EpisodeModel>,
		referenceLocation: EpisodeLatLng,
	): List<EpisodeModel> = episodes.sortedBy { episodeModel ->
		episodeModel.location.distanceTo(referenceLocation)
	}

	private fun setProgrammaticCameraMove(isMoving: Boolean) {
		intent {
			copy(isCameraMovingProgrammatically = isMoving)
		}
	}

	private fun loadGroups() {
		viewModelScope.launch {
			try {
				val userId = userPreferenceDataStore.getUserId().first() ?: throw Exception()
				val group = groupRepository
					.getGroupsByUserId(userId)
					.toPersistentList()
				intent { copy(groups = group) }
			} catch (e: Exception) {
				postSideEffect(HomeSideEffect.ShowToast(R.string.error_group_load_episodes))
			}
		}
	}

	private fun selectGroup(groupId: String) {
		viewModelScope.launch {
			intent { copy(selectedGroupId = groupId) }

			userPreferenceDataStore.updateRecentSelectedGroup(groupId)

			val mostRecentEpisode = episodeRepository.getMostRecentEpisodeByGroup(groupId)
			if (mostRecentEpisode != null) {
				val position = LatLng(
					mostRecentEpisode.location.latitude,
					mostRecentEpisode.location.longitude,
				)
				postSideEffect(HomeSideEffect.MoveCameraToPosition(position))
			} else {
				postSideEffect(HomeSideEffect.MoveCameraToPosition(currentState.cameraPosition.target))
			}
		}
	}
}
