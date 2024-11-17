package com.boostcamp.mapisode.designsystem.compose.menu

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.boostcamp.mapisode.designsystem.compose.Surface

@Composable
fun MapisodeDropdownMenu(
	expanded: Boolean,
	onDismissRequest: () -> Unit,
	modifier: Modifier = Modifier,
	offset: DpOffset = DpOffset(0.dp, 0.dp),
	content: @Composable ColumnScope.() -> Unit
) {
	val density = LocalDensity.current
	val windowSize = rememberWindowSize()
	val transformOriginState = remember { mutableStateOf(TransformOrigin.Center) }

	if (expanded) {
		Popup(
			onDismissRequest = onDismissRequest,
			popupPositionProvider = MapisodeDropdownMenuPositionProvider(
				offset,
				density
			)
		) {
			MapisodeDropdownMenuContent(
				modifier = modifier,
				expandedState = MutableTransitionState(true),
				transformOriginState = transformOriginState,
				scrollState = ScrollState(0),
				shape = RoundedCornerShape(1.dp),
				containerColor = Color.White,
				border = BorderStroke(1.dp, Color.Gray),
				content = content
			)
		}
	}
}

@Composable
internal fun MapisodeDropdownMenuContent(
	modifier: Modifier,
	expandedState: MutableTransitionState<Boolean>,
	transformOriginState: MutableState<TransformOrigin>,
	scrollState: ScrollState,
	shape: Shape,
	containerColor: Color,
	border: BorderStroke?,
	content: @Composable ColumnScope.() -> Unit
) {
	// Menu open/close animation.
	@Suppress("DEPRECATION") val transition = updateTransition(expandedState, "DropDownMenu")

	val scale by
	transition.animateFloat(
		transitionSpec = {
			if (false isTransitioningTo true) {
				// Dismissed to expanded
				tween(durationMillis = InTransitionDuration, easing = LinearOutSlowInEasing)
			} else {
				// Expanded to dismissed.
				tween(durationMillis = 1, delayMillis = OutTransitionDuration - 1)
			}
		},
		label = "FloatAnimation"
	) { expanded ->
		if (expanded) ExpandedScaleTarget else ClosedScaleTarget
	}

	val alpha by
	transition.animateFloat(
		transitionSpec = {
			if (false isTransitioningTo true) {
				// Dismissed to expanded
				tween(durationMillis = 30)
			} else {
				// Expanded to dismissed.
				tween(durationMillis = OutTransitionDuration)
			}
		},
		label = "FloatAnimation"
	) { expanded ->
		if (expanded) ExpandedAlphaTarget else ClosedAlphaTarget
	}

	val isInspecting = LocalInspectionMode.current
	Surface(
		modifier =
		Modifier.graphicsLayer {
			scaleX =
				if (!isInspecting) scale
				else if (expandedState.targetState) ExpandedScaleTarget else ClosedScaleTarget
			scaleY =
				if (!isInspecting) scale
				else if (expandedState.targetState) ExpandedScaleTarget else ClosedScaleTarget
			this.alpha =
				if (!isInspecting) alpha
				else if (expandedState.targetState) ExpandedAlphaTarget else ClosedAlphaTarget
			transformOrigin = transformOriginState.value
		},
		shape = shape,
		color = containerColor,
		border = border,
	) {
		Column(
			modifier =
			modifier
				.padding(vertical = DropdownMenuVerticalPadding)
				.width(IntrinsicSize.Max)
				.verticalScroll(scrollState),
			content = content
		)
	}
}

private class MapisodeDropdownMenuPositionProvider(
	private val offset: DpOffset,
	private val density: Density
) : PopupPositionProvider {
	override fun calculatePosition(
		anchorBounds: IntRect,
		windowSize: IntSize,
		layoutDirection: LayoutDirection,
		popupContentSize: IntSize
	): IntOffset {
		val offsetX = with(density) { offset.x.roundToPx() }
		val offsetY = with(density) { offset.y.roundToPx() }

		val x = anchorBounds.left + offsetX
		val y = anchorBounds.bottom + offsetY

		return IntOffset(
			x.coerceIn(0, windowSize.width - popupContentSize.width),
			y.coerceIn(0, windowSize.height - popupContentSize.height)
		)
	}
}


@Composable
private fun rememberWindowSize(): IntSize {
	val configuration = LocalConfiguration.current
	return remember(configuration) {
		IntSize(
			configuration.screenWidthDp,
			configuration.screenHeightDp
		)
	}
}

internal val MenuVerticalMargin = 48.dp
private val MenuListItemContainerHeight = 48.dp
private val DropdownMenuItemHorizontalPadding = 12.dp
internal val DropdownMenuVerticalPadding = 8.dp
private val DropdownMenuItemDefaultMinWidth = 112.dp
private val DropdownMenuItemDefaultMaxWidth = 280.dp

internal const val InTransitionDuration = 120
internal const val OutTransitionDuration = 75
internal const val ExpandedScaleTarget = 1f
internal const val ClosedScaleTarget = 0.8f
internal const val ExpandedAlphaTarget = 1f
internal const val ClosedAlphaTarget = 0f
