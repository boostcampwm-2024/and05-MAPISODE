package com.boostcamp.mapisode.network.repository

import com.boostcamp.mapisode.model.api.ReverseGeocodeResponse

interface NaverMapsRepository {
	suspend fun reverseGeoCode(coords: String): ReverseGeocodeResponse
}
