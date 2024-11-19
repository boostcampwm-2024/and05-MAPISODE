package com.boostcamp.mapisode.designsystem.compose.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastFirst
import kotlin.math.max

@Composable
fun MapisodeTabBaselineLayout(text: @Composable (() -> Unit)?, icon: @Composable (() -> Unit)?) {
	Layout({
		if (text != null) {
			Box(Modifier.layoutId("text").padding(horizontal = HorizontalTextPadding)) { text() }
		}
		if (icon != null) {
			Box(Modifier.layoutId("icon")) { icon() }
		}
	}) { measurables, constraints ->
		val textPlaceable =
			text?.let {
				measurables
					.fastFirst { it.layoutId == "text" }
					.measure(
						constraints.copy(minHeight = 0)
					)
			}

		val iconPlaceable =
			icon?.let { measurables.fastFirst { it.layoutId == "icon" }.measure(constraints) }

		val tabWidth = max(textPlaceable?.width ?: 0, iconPlaceable?.width ?: 0)

		val specHeight =
			if (textPlaceable != null && iconPlaceable != null) {
				LargeTabHeight
			} else {
				SmallTabHeight
			}
				.roundToPx()

		val tabHeight =
			max(
				specHeight,
				(iconPlaceable?.height ?: 0) +
					(textPlaceable?.height ?: 0) +
					IconDistanceFromBaseline.roundToPx()
			)

		val firstBaseline = textPlaceable?.get(FirstBaseline)
		val lastBaseline = textPlaceable?.get(LastBaseline)

		layout(tabWidth, tabHeight) {
			when {
				textPlaceable != null && iconPlaceable != null ->
					placeTextAndIcon(
						density = this@Layout,
						textPlaceable = textPlaceable,
						iconPlaceable = iconPlaceable,
						tabWidth = tabWidth,
						tabHeight = tabHeight,
						firstBaseline = firstBaseline!!,
						lastBaseline = lastBaseline!!
					)
				textPlaceable != null -> placeTextOrIcon(textPlaceable, tabHeight)
				iconPlaceable != null -> placeTextOrIcon(iconPlaceable, tabHeight)
				else -> {}
			}
		}
	}
}

private fun Placeable.PlacementScope.placeTextOrIcon(
	textOrIconPlaceable: Placeable,
	tabHeight: Int
) {
	val contentY = (tabHeight - textOrIconPlaceable.height) / 2
	textOrIconPlaceable.placeRelative(0, contentY)
}

private fun Placeable.PlacementScope.placeTextAndIcon(
	density: Density,
	textPlaceable: Placeable,
	iconPlaceable: Placeable,
	tabWidth: Int,
	tabHeight: Int,
	firstBaseline: Int,
	lastBaseline: Int
) {
	val baselineOffset =
		if (firstBaseline == lastBaseline) {
			SingleLineTextBaselineWithIcon
		} else {
			DoubleLineTextBaselineWithIcon
		}

	// 마지막 텍스트의 baseline과 탭 레이아웃의 bottom 사이의 전체 offset
	val textOffset =
		with(density) {
			baselineOffset.roundToPx() + 48.dp.roundToPx()
		}

	// 아이콘의 상단(이 레이아웃의 상단)과 텍스트 레이아웃의 bounding box의 상단(기준선이 아님) 사이에 얼마나 공간이 있는지
	val iconOffset =
		with(density) {
			iconPlaceable.height + IconDistanceFromBaseline.roundToPx() - firstBaseline
		}

	val textPlaceableX = (tabWidth - textPlaceable.width) / 2
	val textPlaceableY = tabHeight - lastBaseline - textOffset
	textPlaceable.placeRelative(textPlaceableX, textPlaceableY)

	val iconPlaceableX = (tabWidth - iconPlaceable.width) / 2
	val iconPlaceableY = textPlaceableY - iconOffset
	iconPlaceable.placeRelative(iconPlaceableX, iconPlaceableY)
}

// Tab 높이 사양
private val SmallTabHeight = 42.dp
private val LargeTabHeight = 72.dp

// Tab transition 애니메이션 사양
const val TabFadeInAnimationDuration = 150
const val TabFadeInAnimationDelay = 100
const val TabFadeOutAnimationDuration = 100

private val HorizontalTextPadding = 16.dp
private val SingleLineTextBaselineWithIcon = 14.dp
private val DoubleLineTextBaselineWithIcon = 6.dp
private val IconDistanceFromBaseline = 20.sp
