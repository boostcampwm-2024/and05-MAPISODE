package com.boostcamp.mapisode.network.retrofit

import com.boostcamp.mapisode.network.BuildConfig
import com.boostcamp.mapisode.network.retrofit.NaverMapsApiConstants.BASE_URL
import com.boostcamp.mapisode.network.retrofit.NaverMapsApiConstants.HEADER_API_KEY_ID
import com.boostcamp.mapisode.network.retrofit.NaverMapsApiConstants.HEADER_API_KEY_SECRET
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object NaverMapsApiClient {
	private val okhttpClient = OkHttpClient.Builder()
		.addInterceptor { chain ->
			val requestWithHeaders = chain.request().newBuilder()
				.addHeader(HEADER_API_KEY_ID, BuildConfig.NAVER_MAP_CLIENT_ID)
				.addHeader(HEADER_API_KEY_SECRET, BuildConfig.NAVER_MAP_CLIENT_SECRET)
				.build()
			chain.proceed(requestWithHeaders)
		}
		.build()

	private val jsonConverter = Json {
		ignoreUnknownKeys = true
	}

	internal fun create() = Retrofit.Builder()
		.baseUrl(BASE_URL)
		.client(okhttpClient)
		.addConverterFactory(jsonConverter.asConverterFactory("application/json; charset=UTF-8".toMediaType()))
		.build()
		.create(NaverMapsApi::class.java)
}
