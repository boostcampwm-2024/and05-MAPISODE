package com.boostcamp.mapisode.designsystem.compose.tab

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.boostcamp.mapisode.designsystem.theme.LocalMapisodeContentColor

@Composable
fun MapisodeTabTransition(
	activeColor: Color,
	inactiveColor: Color,
	selected: Boolean,
	content: @Composable () -> Unit
) {
	val transition = updateTransition(selected, label = "")
	val color by
	transition.animateColor(
		transitionSpec = {
			if (false isTransitioningTo true) {
				tween(
					durationMillis = TabFadeInAnimationDuration,
					delayMillis = TabFadeInAnimationDelay,
					easing = LinearEasing
				)
			} else {
				tween(durationMillis = TabFadeOutAnimationDuration, easing = LinearEasing)
			}
		},
		label = ""
	) {
		if (it) activeColor else inactiveColor
	}
	CompositionLocalProvider(LocalMapisodeContentColor provides color, content = content)
}
