package com.boostcamp.mapisode.network.retrofit

import com.boostcamp.mapisode.model.api.ReverseGeocodeResponse
import com.boostcamp.mapisode.network.retrofit.NaverMapsApiConstants.API_ENDPOINT_REVERSE_GEOCODE
import com.boostcamp.mapisode.network.retrofit.NaverMapsApiConstants.QUERY_COORDS
import com.boostcamp.mapisode.network.retrofit.NaverMapsApiConstants.QUERY_ORDERS
import com.boostcamp.mapisode.network.retrofit.NaverMapsApiConstants.QUERY_ORDERS_DEFAULT_ROADADDR
import com.boostcamp.mapisode.network.retrofit.NaverMapsApiConstants.QUERY_OUTPUT
import com.boostcamp.mapisode.network.retrofit.NaverMapsApiConstants.QUERY_OUTPUT_DEFAULT_JSON
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverMapsApi {

	@GET(API_ENDPOINT_REVERSE_GEOCODE)
	suspend fun reverseGeoCoding(
		@Query(QUERY_COORDS) coords: String,
		@Query(QUERY_ORDERS) orders: String? = QUERY_ORDERS_DEFAULT_ROADADDR,
		@Query(QUERY_OUTPUT) output: String? = QUERY_OUTPUT_DEFAULT_JSON,
	): ReverseGeocodeResponse
}
