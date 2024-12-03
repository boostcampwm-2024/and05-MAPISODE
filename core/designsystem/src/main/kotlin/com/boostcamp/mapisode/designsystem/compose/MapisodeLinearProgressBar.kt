package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeLinearProgressBar(
	modifier: Modifier = Modifier,
	progress: Float,
	backgroundColor: Color = Color.Gray,
	progressColor: Color = MapisodeTheme.colorScheme.circularIndicator,
) {
	Canvas(modifier = modifier) {
		val width = size.width
		val height = size.height
		val cornerRadius = height / 2

		// 배경 바
		drawRoundRect(
			color = backgroundColor,
			size = Size(width, height),
			cornerRadius = CornerRadius(cornerRadius, cornerRadius),
		)

		// 프로그레스 바
		drawRoundRect(
			color = progressColor,
			size = Size(width * progress, height),
			cornerRadius = CornerRadius(cornerRadius, cornerRadius),
		)
	}
}

@Preview(showBackground = true)
@Composable
fun MapisodeLinearProgressBarPreview() {
	MapisodeLinearProgressBar(
		modifier = Modifier
			.fillMaxWidth()
			.height(10.dp),
		progress = 0.5f,
	)
}
