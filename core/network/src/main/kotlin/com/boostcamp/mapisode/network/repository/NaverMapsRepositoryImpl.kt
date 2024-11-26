package com.boostcamp.mapisode.network.repository

import com.boostcamp.mapisode.network.response.ReverseGeocodeResponse
import com.boostcamp.mapisode.network.retrofit.NaverMapsApi
import okio.IOException
import timber.log.Timber
import javax.inject.Inject

class NaverMapsRepositoryImpl @Inject constructor(private val naverMapsApi: NaverMapsApi) :
	NaverMapsRepository {
	override suspend fun reverseGeoCode(coords: String): Result<String> =
		try {
			val response: ReverseGeocodeResponse = naverMapsApi.reverseGeoCoding(coords)
			val address = buildString {
				response.results?.firstOrNull()?.let {
					// 시 + 구
					append("${it.region?.area1?.name} ")
					append("${it.region?.area2?.name} ")
					if (it.name == "roadaddr") {
						// 도로명주소가 존재하는 경우 도로명 + 번호
						append("${it.land?.name} ")
						append("${it.land?.number1}")
					} else {
						// 도로명주소가 존재하지 않는 경우 동 + 건물번호
						append("${it.region?.area3?.name} ")
						append("${it.land?.number1}")
						if (it.land?.number2 != null && it.land.number2.isNotEmpty()) {
							append("-")
							append("${it.land.number2}")
						}
					}
				}
			}
			Result.success(address)
		} catch (e: retrofit2.HttpException) {
			Timber.e(e)
			Result.failure(e)
		} catch (e: IOException) {
			Timber.e(e)
			Result.failure(e)
		}
}
