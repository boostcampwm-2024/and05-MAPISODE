package com.boostcamp.mapisode.designsystem.compose.ripple

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.radialGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object MapisodeRippleAIndication : IndicationNodeFactory {

	override fun create(interactionSource: InteractionSource): DelegatableNode =
		MapisodeIndicationInstance(interactionSource)

	override fun hashCode(): Int = -1

	override fun equals(other: Any?) = other === this

	private class MapisodeIndicationInstance(private val interactionSource: InteractionSource) :
		Modifier.Node(),
		DrawModifierNode {
		var currentPressPosition: Offset = Offset.Zero
		val animatedProgress = Animatable(0f)
		val animatedPressAlpha = Animatable(1f)

		var pressedAnimation: Job? = null
		var restingAnimation: Job? = null

		private suspend fun animateToPressed(pressPosition: Offset) {
			restingAnimation?.cancel()
			pressedAnimation?.cancel()
			pressedAnimation = coroutineScope.launch {
				currentPressPosition = pressPosition
				animatedPressAlpha.snapTo(1f)
				animatedProgress.snapTo(0f)
				animatedProgress.animateTo(1f, tween(450))
			}
		}

		private fun animateToResting() {
			restingAnimation = coroutineScope.launch {
				pressedAnimation?.join()
				animatedPressAlpha.animateTo(0f, tween(50))
				animatedProgress.snapTo(0f)
			}
		}

		override fun onAttach() {
			coroutineScope.launch {
				interactionSource.interactions.collect { interaction ->
					when (interaction) {
						is PressInteraction.Press -> animateToPressed(interaction.pressPosition)
						is PressInteraction.Release -> animateToResting()
						is PressInteraction.Cancel -> animateToResting()
					}
				}
			}
		}

		override fun ContentDrawScope.draw() {
			val maxDimension = size.maxDimension
			val brush = animateBrush(
				center = currentPressPosition,
				radius = maxDimension * animatedProgress.value,
			)
			val alpha = animatedPressAlpha.value

			drawContent()
			drawRect(
				brush = brush,
				alpha = alpha * 0.3f,
			)
		}

		private fun animateBrush(
			center: Offset,
			radius: Float,
		): Brush {
			if (radius == 0f) return TransparentBrush

			return radialGradient(
				colors = listOf(White, Black),
				center = center,
				radius = radius,
			)
		}

		companion object {
			val TransparentBrush = SolidColor(Color.Transparent)
			val White = Color.White
			val Black = Color.Black
		}
	}
}
