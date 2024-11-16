package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

class MapisodeRippleState {
	var rippleCenter by mutableStateOf(Offset.Zero)
	var rippleRadius by mutableFloatStateOf(0f)
}

fun Modifier.mapisodeRippleEffect(
	enabled: Boolean = true,
	rippleColor: Color = Color.White.copy(alpha = 0.3f),
): Modifier = composed {
	val rippleState = remember { MapisodeRippleState() }
	val coroutineScope = rememberCoroutineScope()

	Modifier
		.graphicsLayer(alpha = 0.99f)
		.drawWithContent {
			drawContent()
			if (rippleState.rippleRadius > 0) {
				drawCircle(
					color = rippleColor,
					radius = rippleState.rippleRadius,
					center = rippleState.rippleCenter,
				)
			}
		}
		.pointerInput(enabled) {
			if (enabled) {
				awaitPointerEventScope {
					while (true) {
						val event = awaitPointerEvent()
						val pointer = event.changes.firstOrNull()

						if (pointer != null && pointer.pressed) {
							rippleState.rippleCenter = pointer.position
							coroutineScope.launch {
								val maxRadius = size.width.coerceAtLeast(size.height) * 1.5f

								animate(
									initialValue = 0f,
									targetValue = maxRadius,
									animationSpec = tween(50),
								) { value, _ ->
									rippleState.rippleRadius = value
								}

								animate(
									initialValue = maxRadius,
									targetValue = 0f,
									animationSpec = tween(50),
								) { value, _ ->
									rippleState.rippleRadius = value
								}
							}
						}
					}
				}
			}
		}
}
