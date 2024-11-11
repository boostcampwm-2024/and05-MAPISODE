package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

internal val minBorderWidth = 0.34.dp
internal val normalBorderWidth = 1.dp
internal val thickBorderWidth = 8.dp

enum class MapisodeBorder(val dp: Dp) {
	Thin(minBorderWidth),
	Normal(normalBorderWidth),
	Thick(thickBorderWidth),
}

enum class Direction {
	Horizontal,
	Vertical,
}

enum class Thickness(val value: Dp) {
	Thin(MapisodeBorder.Thin.dp),
	Thick(MapisodeBorder.Thick.dp),
}

@Composable
fun RowScope.MapisodeDivider(
	thickness: Thickness,
	modifier: Modifier = Modifier,
) {
	MapisodeDivider(
		direction = Direction.Vertical,
		thickness = thickness,
		modifier = modifier,
	)
}

@Composable
fun ColumnScope.MapisodeDivider(
	thickness: Thickness,
	modifier: Modifier = Modifier,
) {
	MapisodeDivider(
		direction = Direction.Horizontal,
		thickness = thickness,
		modifier = modifier,
	)
}

@Composable
fun MapisodeDivider(
	direction: Direction,
	thickness: Thickness,
	modifier: Modifier = Modifier,
) {
	Box(
		modifier = Modifier
			.then(modifier)
			.background(
				color = when (thickness) {
					Thickness.Thin -> MapisodeTheme.colorScheme.hintStroke
					Thickness.Thick -> MapisodeTheme.colorScheme.background
				},
			)
			.then(
				when (direction) {
					Direction.Horizontal ->
						Modifier
							.fillMaxWidth()
							.height(thickness.value)

					Direction.Vertical ->
						Modifier
							.fillMaxHeight()
							.width(thickness.value)
				},
			),
	)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun DividerPreview() {
	MapisodeTheme {
		Column(modifier = Modifier.height(IntrinsicSize.Min)) {
			MapisodeText(text = "Thick Horizontal")
			MapisodeDivider(thickness = Thickness.Thick)
			MapisodeText(text = "Divier")

			Spacer(Modifier.height(8.dp))

			Row(modifier = Modifier.height(IntrinsicSize.Min)) {
				MapisodeText(
					text = "Thin Vertical",
					modifier = Modifier.padding(end = 8.dp),
				)
				MapisodeDivider(thickness = Thickness.Thin)
				MapisodeText(
					text = "Divider",
					modifier = Modifier.padding(start = 8.dp),
				)
			}
		}
	}
}
