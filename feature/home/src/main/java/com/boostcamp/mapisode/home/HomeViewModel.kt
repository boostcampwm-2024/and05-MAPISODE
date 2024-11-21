package com.boostcamp.mapisode.home

import androidx.lifecycle.viewModelScope
import com.boostcamp.mapisode.episode.EpisodeRepository
import com.boostcamp.mapisode.home.common.ChipType
import com.boostcamp.mapisode.home.common.HomeConstant.DEFAULT_ZOOM
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.ui.base.BaseViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val episodeRepository: EpisodeRepository,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

	fun onIntent(intent: HomeIntent) {
		when (intent) {
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
					postSideEffect(HomeSideEffect.ShowToast(R.string.home_location_permission_needed))
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
				loadEpisodes(intent.start, intent.end)
			}
		}
	}

	private fun setSelectedChip(chipType: ChipType) {
		intent {
			copy(selectedChip = chipType)
		}
		// TODO: 여기에 선택된 Chip에 따라 데이터를 조회하는 로직을 추가
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

	private fun loadEpisodes(start: EpisodeLatLng, end: EpisodeLatLng) {
		viewModelScope.launch {
			try {
				val groupId = "FwQVGHMot4BVS4nMAJHg" // TODO 캐싱된 그룹 가져오기 (현재는 임시로 하드코딩)
				val category = currentState.selectedChip?.name

				val episodes = episodeRepository.getEpisodesByGroupAndLocation(
					groupId = groupId,
					start = start,
					end = end,
					category = category,
				)

				intent { copy(episodes = episodes.toPersistentList()) }
			} catch (e: Exception) {
				postSideEffect(HomeSideEffect.ShowToast(R.string.error_load_episodes))
			}
		}
	}
}
