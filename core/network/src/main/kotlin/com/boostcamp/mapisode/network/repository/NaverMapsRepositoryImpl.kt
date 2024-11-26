package com.boostcamp.mapisode.network.repository

import com.boostcamp.mapisode.model.api.ReverseGeocodeResponse
import com.boostcamp.mapisode.network.retrofit.NaverMapsApi
import javax.inject.Inject

class NaverMapsRepositoryImpl @Inject constructor(private val naverMapsApi: NaverMapsApi) :
	NaverMapsRepository {
	override suspend fun reverseGeoCode(coords: String): ReverseGeocodeResponse =
		naverMapsApi.reverseGeoCoding(coords)
}
