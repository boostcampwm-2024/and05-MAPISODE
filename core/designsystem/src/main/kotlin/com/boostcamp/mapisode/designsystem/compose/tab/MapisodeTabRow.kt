package com.boostcamp.mapisode.designsystem.compose.tab

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import com.boostcamp.mapisode.designsystem.compose.Direction
import com.boostcamp.mapisode.designsystem.compose.MapisodeDivider
import com.boostcamp.mapisode.designsystem.compose.Surface
import com.boostcamp.mapisode.designsystem.compose.Thickness
import com.boostcamp.mapisode.designsystem.compose.tab.TabRowDefaults.tabIndicatorOffset
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
@UiComposable
fun MapisodeTabRow(
	selectedTabIndex: Int,
	modifier: Modifier = Modifier,
	backgroundColor: Color = MapisodeTheme.colorScheme.tabBackground,
	contentColor: Color = MapisodeTheme.colorScheme.tabItemTextSelected,
	indicator: @Composable @UiComposable
		(tabPositions: List<TabPosition>) -> Unit = {tabPositions ->
		Box(
			Modifier
				.tabIndicatorOffset(tabPositions[selectedTabIndex])
				.height(3.dp)
				.background(MapisodeTheme.colorScheme.tabIndicator),
		)
	},
	divider: @Composable @UiComposable () -> Unit =
		@Composable {
			MapisodeDivider(
				direction = Direction.Horizontal,
				thickness = Thickness.Thin,
				modifier = modifier,
				color = MapisodeTheme.colorScheme.dividerThinColor,
			)
		},
	tabs: @Composable @UiComposable () -> Unit,
) {
	Surface(
		modifier = modifier.selectableGroup(),
		color = backgroundColor,
		contentColor = contentColor,
	) {
		SubcomposeLayout(Modifier.fillMaxWidth()) { constraints ->
			val tabRowWidth = constraints.maxWidth
			val tabMeasurables = subcompose(TabSlots.Tabs, tabs)
			val tabCount = tabMeasurables.size
			val tabWidth = (tabRowWidth / tabCount)
			val tabPlaceables = tabMeasurables.fastMap {
				it.measure(constraints.copy(minWidth = tabWidth, maxWidth = tabWidth))
			}

			val tabRowHeight = tabPlaceables.fastMaxBy { it.height }?.height ?: 0

			val tabPositions = List(tabCount) { index ->
				TabPosition(tabWidth.toDp() * index, tabWidth.toDp())
			}

			layout(tabRowWidth, tabRowHeight) {
				tabPlaceables.fastForEachIndexed { index, placeable ->
					placeable.placeRelative(index * tabWidth, 0)
				}

				subcompose(TabSlots.Divider, divider).fastForEach {
					val placeable = it.measure(constraints.copy(minHeight = 0))
					placeable.placeRelative(0, tabRowHeight - placeable.height)
				}

				subcompose(TabSlots.Indicator) {
					indicator(tabPositions)
				}.fastForEach {
					it.measure(Constraints.fixed(tabRowWidth, tabRowHeight)).placeRelative(0, 0)
				}
			}
		}
	}
}

object TabRowDefaults {

	fun Modifier.tabIndicatorOffset(
		currentTabPosition: TabPosition,
	): Modifier = composed(
		inspectorInfo = debugInspectorInfo {
			name = "tabIndicatorOffset"
			value = currentTabPosition
		},
	) {
		val currentTabWidth by animateDpAsState(
			targetValue = currentTabPosition.width,
			animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = "",
		)
		val indicatorOffset by animateDpAsState(
			targetValue = currentTabPosition.left,
			animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = "",
		)
		fillMaxWidth()
			.wrapContentSize(Alignment.BottomStart)
			.offset { IntOffset(x = indicatorOffset.roundToPx(), y = 0) }
			.width(currentTabWidth)
	}
}

@Immutable
class TabPosition internal constructor(val left: Dp, val width: Dp)

enum class TabSlots {
	Tabs,
	Divider,
	Indicator
}
