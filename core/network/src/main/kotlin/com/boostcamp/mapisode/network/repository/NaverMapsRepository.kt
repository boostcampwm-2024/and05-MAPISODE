package com.boostcamp.mapisode.network.repository

interface NaverMapsRepository {
	suspend fun reverseGeoCode(coords: String): Result<String>
}
