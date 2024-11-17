package com.boostcamp.mapisode.designsystem.compose.ripple

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.invalidateDraw
import kotlinx.coroutines.launch

object MapisodeRippleBIndication : IndicationNodeFactory {

	override fun create(interactionSource: InteractionSource): DelegatableNode =
		MapisodeIndicationInstance(interactionSource)

	override fun hashCode(): Int = -1

	override fun equals(other: Any?) = other === this

	private class MapisodeIndicationInstance(private val interactionSource: InteractionSource) :
		Modifier.Node(),
		DrawModifierNode {
		private var isPressed = false
		private var isHovered = false
		private var isFocused = false
		override fun onAttach() {
			coroutineScope.launch {
				var pressCount = 0
				var hoverCount = 0
				var focusCount = 0
				interactionSource.interactions.collect { interaction ->
					when (interaction) {
						is PressInteraction.Press -> pressCount++
						is PressInteraction.Release -> pressCount--
						is PressInteraction.Cancel -> pressCount--
						is HoverInteraction.Enter -> hoverCount++
						is HoverInteraction.Exit -> hoverCount--
						is FocusInteraction.Focus -> focusCount++
						is FocusInteraction.Unfocus -> focusCount--
					}
					val pressed = pressCount > 0
					val hovered = hoverCount > 0
					val focused = focusCount > 0
					var invalidateNeeded = false
					if (isPressed != pressed) {
						isPressed = pressed
						invalidateNeeded = true
					}
					if (isHovered != hovered) {
						isHovered = hovered
						invalidateNeeded = true
					}
					if (isFocused != focused) {
						isFocused = focused
						invalidateNeeded = true
					}
					if (invalidateNeeded) invalidateDraw()
				}
			}
		}

		override fun ContentDrawScope.draw() {
			drawContent()
			if (isPressed) {
				drawCircle(
					color = Color.Gray.copy(alpha = 0.4f),
					radius = size.maxDimension
				)
			} else if (isHovered || isFocused) {
				drawCircle(
					color = Color.LightGray.copy(alpha = 0.4f),
					radius = size.maxDimension
				)
			}
		}
	}
}
