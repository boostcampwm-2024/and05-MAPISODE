package com.boostcamp.mapisode.designsystem.compose.toast

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RecomposeScope
import androidx.compose.runtime.State
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

data class ToastTransitionItem(val toastData: ToastData?, val opacityTransition: OpacityTransition)

typealias OpacityTransition = @Composable (toast: @Composable () -> Unit) -> Unit

@Composable
fun FadeInFadeOut(
	newToastData: ToastData?,
	modifier: Modifier = Modifier,
	toast: @Composable (ToastData) -> Unit,
) {
	var scheduledToastData by remember { mutableStateOf<ToastData?>(null) }
	val toastTransitions = remember { mutableListOf<ToastTransitionItem>() }
	var scope by remember { mutableStateOf<RecomposeScope?>(null) }

	if (newToastData != scheduledToastData) {
		scheduledToastData = newToastData
		val toastDataList = toastTransitions.map {
			it.toastData
		}.toMutableList()
		toastDataList.add(newToastData)
		toastTransitions.clear()
		toastDataList
			.filterNotNull()
			.mapTo(destination = toastTransitions) { appearedToastData ->
				ToastTransitionItem(appearedToastData) { toast ->
					val isVisible = appearedToastData == newToastData
					val opacity = animatedOpacity(
						animation = tween(
							easing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f),
							delayMillis = 0,
							durationMillis = 250,
						),
						visible = isVisible,
						onAnimationFinish = {
							if (appearedToastData != scheduledToastData) {
								toastTransitions.removeAll { it.toastData == appearedToastData }
								scope?.invalidate()
							}
						},
					)
					Box(
						Modifier
							.graphicsLayer(
								alpha = opacity.value,
							),
					) {
						toast()
					}
				}
			}
	}

	Box(modifier) {
		scope = currentRecomposeScope
		toastTransitions.forEach { (toastData, opacity) ->
			key(toastData) {
				opacity {
					toast(toastData!!)
				}
			}
		}
	}
}

@Composable
fun animatedOpacity(
	animation: AnimationSpec<Float>,
	visible: Boolean,
	onAnimationFinish: () -> Unit = {},
): State<Float> {
	val alpha = remember { Animatable(if (!visible) 1f else 0f) }
	LaunchedEffect(visible) {
		alpha.animateTo(
			if (visible) 1f else 0f,
			animationSpec = animation,
		)
		onAnimationFinish()
	}
	return alpha.asState()
}
