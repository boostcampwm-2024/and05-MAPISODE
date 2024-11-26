package com.boostcamp.mapisode.common.util

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
	val earthRadius = 6371e3 // 지구 반지름 (미터 단위)

	val dLat = Math.toRadians(lat2 - lat1)
	val dLon = Math.toRadians(lon2 - lon1)

	val a = sin(dLat / 2).pow(2.0) +
		cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
		sin(dLon / 2).pow(2.0)

	val c = 2 * atan2(sqrt(a), sqrt(1 - a))

	return earthRadius * c // 미터 단위 거리 반환
}
