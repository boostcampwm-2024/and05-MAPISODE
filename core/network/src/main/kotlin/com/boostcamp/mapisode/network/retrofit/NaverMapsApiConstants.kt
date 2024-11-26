package com.boostcamp.mapisode.network.retrofit

object NaverMapsApiConstants {
	internal const val BASE_URL = "https://naveropenapi.apigw.ntruss.com/"
	internal const val API_ENDPOINT_REVERSE_GEOCODE = "map-reversegeocode/v2/gc"

	internal const val HEADER_API_KEY_ID = "x-ncp-apigw-api-key-id"
	internal const val HEADER_API_KEY_SECRET = "x-ncp-apigw-api-key"

	internal const val QUERY_COORDS = "coords"
	internal const val QUERY_ORDERS = "orders"
	internal const val QUERY_OUTPUT = "output"

	internal const val QUERY_ORDERS_DEFAULT = "roadaddr,addr"
	internal const val QUERY_OUTPUT_DEFAULT_JSON = "json"
}
