package com.boostcamp.mapisode.common.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.throttleFirst(interval: Long): Flow<T> {
	require(interval > 0) { "Exception: Period should be positive!!!" }
	return flow {
		var lastTimeMillis = 0L
		collect { value ->
			val currentTimeMillis = System.currentTimeMillis()
			if (currentTimeMillis - lastTimeMillis >= interval) {
				lastTimeMillis = currentTimeMillis
				emit(value)
			}
		}
	}
}
