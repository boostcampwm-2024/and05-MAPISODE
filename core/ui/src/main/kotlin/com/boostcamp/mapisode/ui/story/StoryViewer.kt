package com.boostcamp.mapisode.ui.story

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.PersistentList

@Composable
fun StoryViewer(
	imageUrls: PersistentList<String>,
	authorName: String,
	authorProfileUrl: String,
	onClose: () -> Unit = {},
) {
	var currentIndex by rememberSaveable { mutableIntStateOf(0) }
	val progressAnimatable = remember { Animatable(0f) }
	var isPaused by rememberSaveable { mutableStateOf(false) }

	LaunchedEffect(
		key1 = currentIndex,
		key2 = isPaused,
	) {
		if (!isPaused) {
			progressAnimatable.snapTo(0f)
			progressAnimatable.animateTo(
				targetValue = 1f,
				animationSpec = tween(durationMillis = 5000),
			)
			// 시간이 다 지나면 다음 이미지로
			if (currentIndex < imageUrls.size - 1) {
				currentIndex += 1
			} else {
				onClose()
			}
		}
	}
}
