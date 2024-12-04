package com.boostcamp.mapisode.ui.story

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.test.FakeImage
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.AppTypography
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

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
	val coroutineScope = rememberCoroutineScope()
	var isClosed by rememberSaveable { mutableStateOf(false) }

	LaunchedEffect(
		key1 = currentIndex,
		key2 = isPaused,
	) {
		if (!isPaused) {
			progressAnimatable.stop()
			progressAnimatable.snapTo(0f)
			progressAnimatable.animateTo(
				targetValue = 1f,
				animationSpec = tween(durationMillis = 7000),
			)
			if (progressAnimatable.value == 1f) {
				// 다음 사진으로 이동
				if (currentIndex < imageUrls.size - 1) {
					currentIndex += 1
				} else {
					if (!isClosed) {
						isClosed = true
						onClose()
					}
				}
			}
		}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.pointerInput(Unit) {
				detectTapGestures(
					onPress = {
						isPaused = true
						tryAwaitRelease()
						isPaused = false
					},
					onTap = { offset ->
						val width = size.width
						coroutineScope.launch {
							if (offset.x < width / 2) {
								// 왼쪽 터치 동작
								if (progressAnimatable.value > 0.1f) {
									progressAnimatable.snapTo(0f)
								} else if (currentIndex > 0) {
									currentIndex -= 1
									progressAnimatable.snapTo(0f)
								} else {
									if (!isClosed) {
										isClosed = true
										onClose()
									}
								}
							} else {
								// 오른쪽 터치 동작
								if (currentIndex < imageUrls.size - 1) {
									currentIndex += 1
									progressAnimatable.snapTo(0f)
								} else {
									if (!isClosed) {
										isClosed = true
										onClose()
									}
								}
							}
						}
					},
				)
			},
	) {
		AsyncImage(
			model = imageUrls[currentIndex],
			contentDescription = "애피소드 이미지",
			modifier = Modifier.fillMaxSize(),
			contentScale = ContentScale.FillWidth,
		)

		StoryProgressBars(
			imageCount = imageUrls.size,
			currentIndex = currentIndex,
			progress = progressAnimatable.value,
			modifier = Modifier
				.align(Alignment.TopCenter)
				.padding(top = 16.dp, start = 12.dp, end = 12.dp),
		)

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)
				.align(Alignment.BottomStart),
		) {
			AsyncImage(
				model = authorProfileUrl,
				contentDescription = "유저 프로필 이미지",
				modifier = Modifier
					.size(48.dp)
					.clip(CircleShape),
				contentScale = ContentScale.Crop,
			)

			Spacer(modifier = Modifier.width(8.dp))

			MapisodeText(
				text = authorName,
				color = Color.White,
				modifier = Modifier.align(Alignment.CenterVertically),
				style = AppTypography.bodyLarge,
			)
		}
	}
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StoryViewerPreview() {
	val previewHandler = AsyncImagePreviewHandler {
		FakeImage(color = 0xFFE0E0E0.toInt())
	}

	CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
		StoryViewer(
			imageUrls = persistentListOf(
				"https://github.com/user-attachments/assets/502a3888-7a98-420c-93e1-1286d340f9dc",
				"https://github.com/user-attachments/assets/502a3888-7a98-420c-93e1-1286d340f9dc",
				"https://github.com/user-attachments/assets/502a3888-7a98-420c-93e1-1286d340f9dc",
			),
			authorName = "haeti",
			authorProfileUrl = "",
		)
	}
}
