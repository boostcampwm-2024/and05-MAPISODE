package com.boostcamp.mapisode.common.util

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

private const val DEFAULT_THROTTLE_TIME = 500L

@Composable
fun Modifier.throttleClickable(
	throttleTime: Long = DEFAULT_THROTTLE_TIME,
	onClick: () -> Unit,
): Modifier {

	var lastClickTime by rememberSaveable { mutableLongStateOf(0L) }

	return this.clickable {
		val currentTime: Long = System.currentTimeMillis()

		if (currentTime - lastClickTime < throttleTime) {
			return@clickable
		}

		onClick()
		lastClickTime = currentTime
	}
}
