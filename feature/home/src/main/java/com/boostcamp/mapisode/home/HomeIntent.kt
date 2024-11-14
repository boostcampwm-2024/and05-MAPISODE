package com.boostcamp.mapisode.home

import com.naver.maps.geometry.LatLng

sealed class HomeIntent {
	data object RequestLocationPermission : HomeIntent()
	data class SetInitialLocation(val latLng: LatLng) : HomeIntent()
	data class UpdateLocationPermission(val isGranted: Boolean) : HomeIntent()
	data object MarkPermissionRequested : HomeIntent()
}
