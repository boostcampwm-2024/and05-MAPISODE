package com.boostcamp.mapisode.ui.story

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.MapisodeLinearProgressBar

@Composable
fun StoryProgressBars(
	modifier: Modifier = Modifier,
	imageCount: Int,
	currentIndex: Int,
	progress: Float,
	barHeight: Dp = 10.dp,
	spacing: Dp = 6.dp,
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.background(Color.Transparent),
		horizontalArrangement = Arrangement.spacedBy(spacing),
	) {
		repeat(imageCount) { index ->
			Box(
				modifier = Modifier
					.weight(1f)
					.height(barHeight)
					.background(Color.Transparent),
			) {
				MapisodeLinearProgressBar(
					modifier = Modifier
						.fillMaxSize()
						.clip(RoundedCornerShape(barHeight / 2)),
					progress = when {
						index < currentIndex -> 1f
						index == currentIndex -> progress
						else -> 0f
					},
				)
			}
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StoryProgressBarsPreview() {
	StoryProgressBars(
		imageCount = 5,
		currentIndex = 2,
		progress = 0.5f,
	)
}
