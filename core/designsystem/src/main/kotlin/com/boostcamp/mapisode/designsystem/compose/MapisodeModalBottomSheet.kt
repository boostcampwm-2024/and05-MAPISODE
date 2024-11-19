package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun MapisodeModalBottomSheet(
	isVisible: Boolean,
	onDismiss: () -> Unit,
	modifier: Modifier = Modifier,
	cornerRadius: Dp = 16.dp,
	backgroundColor: Color = Color.White,
	scrimColor: Color = Color.Black.copy(alpha = 0.32f),
	dragHandle: Boolean = true,
	sheetContent: @Composable () -> Unit,
) {
	val scope = rememberCoroutineScope()
	var bottomSheetHeight by remember { mutableFloatStateOf(0f) }
	var isDragging by remember { mutableStateOf(false) }
	val offsetY = remember { Animatable(if (isVisible) 0f else bottomSheetHeight) }

	LaunchedEffect(isVisible) {
		if (isVisible) {
			offsetY.animateTo(
				targetValue = 100f,
				animationSpec = spring(
					dampingRatio = Spring.DampingRatioMediumBouncy,
					stiffness = Spring.StiffnessLow,
				),
			)
		} else {
			offsetY.animateTo(
				targetValue = bottomSheetHeight,
				animationSpec = spring(
					dampingRatio = Spring.DampingRatioNoBouncy,
					stiffness = Spring.StiffnessMedium,
				),
			)
		}
	}

	val backgroundAlpha by animateFloatAsState(
		targetValue = if (isVisible) 1f else 0f,
		animationSpec = tween(300),
		label = "backgroundAlpha",
	)

	if (isVisible || offsetY.value < bottomSheetHeight) {
		Box(
			modifier = modifier.fillMaxSize(),
		) {
			// 스크림(어두운 배경)
			Box(
				modifier = Modifier
					.fillMaxSize()
					.alpha(backgroundAlpha)
					.background(scrimColor)
					.pointerInput(Unit) {
						detectTapGestures { onDismiss() }
					},
			)

			// 바텀시트
			Box(
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.offset {
						IntOffset(0, offsetY.value.roundToInt())
					}
					.clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius))
					.background(backgroundColor)
					.onGloballyPositioned {
						if (bottomSheetHeight == 0f) {
							bottomSheetHeight = it.size.height.toFloat()
						}
					}
					.pointerInput(Unit) {
						val velocityTracker = VelocityTracker()

						detectVerticalDragGestures(
							onDragStart = { isDragging = true },
							onDragEnd = {
								isDragging = false
								val velocity = velocityTracker.calculateVelocity()

								scope.launch {
									if (velocity.y > 1000f || offsetY.value > bottomSheetHeight * 0.8f) {
										onDismiss()
									} else {
										offsetY.animateTo(
											targetValue = 0f,
											animationSpec = spring(
												dampingRatio = Spring.DampingRatioMediumBouncy,
												stiffness = Spring.StiffnessLow,
											),
										)
									}
								}
							},
							onDragCancel = { isDragging = false },
							onVerticalDrag = { change, dragAmount ->
								velocityTracker.addPosition(
									change.uptimeMillis,
									change.position,
								)
								scope.launch {
									offsetY.snapTo((offsetY.value + dragAmount).coerceAtLeast(0f))
								}
							},
						)
					},
			) {
				Column(modifier = Modifier.fillMaxWidth()) {
					if (dragHandle) {
						Box(
							modifier = Modifier
								.padding(vertical = 12.dp)
								.align(Alignment.CenterHorizontally)
								.width(32.dp)
								.height(4.dp)
								.clip(RoundedCornerShape(2.dp))
								.background(Color.LightGray),
						)
					}
					Box(modifier = Modifier.fillMaxWidth()) {
						sheetContent()
					}
				}
			}
		}
	}
}
