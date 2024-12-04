package com.boostcamp.mapisode.designsystem.compose.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.boostcamp.mapisode.designsystem.theme.MapisodeTextStyle
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

private const val DEFAULT_THROTTLE_INTERVAL = 1000L

@Composable
fun MapisodeThrottleFilledButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	throttleInterval: Long = DEFAULT_THROTTLE_INTERVAL,
	text: String,
	disabledText: String = text,
	textStyle: MapisodeTextStyle = MapisodeTheme.typography.titleLarge,
	enabled: Boolean = true,
	@DrawableRes leftIcon: Int? = null,
	@DrawableRes rightIcon: Int? = null,
	showRipple: Boolean = false,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
	val lastClickTime = remember { mutableLongStateOf(0L) }

	val throttledClick: () -> Unit = {
		val currentTime: Long = System.currentTimeMillis()
		if (currentTime - lastClickTime.longValue >= throttleInterval) {
			onClick()
			lastClickTime.longValue = currentTime
		}
	}

	MapisodeFilledButton(
		onClick = throttledClick,
		modifier = modifier,
		text = text,
		disabledText = disabledText,
		textStyle = textStyle,
		enabled = enabled,
		leftIcon = leftIcon,
		rightIcon = rightIcon,
		showRipple = showRipple,
		interactionSource = interactionSource,
	)
}
