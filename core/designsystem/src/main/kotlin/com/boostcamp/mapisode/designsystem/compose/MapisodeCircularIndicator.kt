package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeCircularLoadingIndicator(
	modifier: Modifier = Modifier,
	circleSize: Dp = 50.dp,
	borderWidth: Dp = 3.dp,
	color: Color = MapisodeTheme.colorScheme.circularIndicator,
	animationDuration: Int = 800,
	cap: StrokeCap = StrokeCap.Butt,
) {
	val infiniteTransition = rememberInfiniteTransition(label = "무한 애니메이션")
	val animatedProgress = infiniteTransition.animateFloat(
		initialValue = -90f,
		targetValue = 270f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing),
		),
		label = "애니메이션 인디케이터",
	)

	Canvas(modifier = modifier.size(circleSize)) {
		val arcSize = circleSize.toPx() - borderWidth.toPx()

		drawArc(
			color = color,
			startAngle = animatedProgress.value,
			sweepAngle = 240f,
			useCenter = false,
			style = Stroke(width = borderWidth.toPx(), cap = cap),
			size = Size(arcSize, arcSize),
		)
	}
}
