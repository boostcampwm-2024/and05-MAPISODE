package com.boostcamp.mapisode.designsystem.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.theme.LocalMapisodeIconColor
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

sealed class IconSize(val value: Dp) {
	data object EExtraSmall : IconSize(12.dp)
	data object ExtraSmall : IconSize(16.dp)
	data object Small : IconSize(20.dp)
	data object Medium : IconSize(24.dp)
	data object Normal : IconSize(30.dp)
	data object Large : IconSize(48.dp)
}

@Composable
fun MapisodeIcon(
	@DrawableRes id: Int,
	modifier: Modifier = Modifier,
	contentDescription: String? = null,
	iconSize: IconSize = IconSize.Medium,
	tint: Color? = LocalMapisodeIconColor.current.let {
		if (it == Color.Unspecified) MapisodeTheme.colorScheme.iconColor else it
	},
) {
	val colorFilter = tint?.let { ColorFilter.tint(it) }
	val semantics = if (contentDescription != null) {
		Modifier.semantics {
			this.contentDescription = contentDescription
			this.role = Role.Image
		}
	} else {
		Modifier
	}
	Box(
		modifier
			.toolingGraphicsLayer()
			.size(iconSize.value)
			.paint(
				painter = painterResource(id = id),
				colorFilter = colorFilter,
				contentScale = ContentScale.Fit,
			)
			.then(semantics),
	)
}

@Preview(showBackground = true)
@Composable
fun IconSizeComparisonPreview() {
	val iconId = R.drawable.ic_account_circle
	val iconSizes = listOf(
		IconSize.EExtraSmall,
		IconSize.ExtraSmall,
		IconSize.Small,
		IconSize.Medium,
		IconSize.Normal,
		IconSize.Large,
	)

	Column(
		modifier = Modifier
			.padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		iconSizes.forEach { iconSize ->
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(4.dp),
			) {
				MapisodeText(
					text = iconSize::class.simpleName ?: "Unknown",
					style = MapisodeTheme.typography.labelMedium,
				)
				MapisodeIcon(
					id = iconId,
					iconSize = iconSize,
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun MarkerIconsPreview() {
	LazyVerticalGrid(
		columns = GridCells.Fixed(2),
		modifier = Modifier.padding(0.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {

		items(
			listOf(
				Pair("ic_eat_marker_light", R.drawable.ic_eat_marker_light),
				Pair("ic_eat_marker_dark", R.drawable.ic_eat_marker_dark),
				Pair("ic_see_marker_light", R.drawable.ic_see_marker_light),
				Pair("ic_see_marker_dark", R.drawable.ic_see_marker_dark),
				Pair("ic_other_marker_light", R.drawable.ic_other_marker_light),
				Pair("ic_other_marker_dark", R.drawable.ic_other_marker_dark),
			),
		) { item ->
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(4.dp),
			) {
				MapisodeText(
					text = item.first,
					style = MapisodeTheme.typography.labelMedium,
				)
				MapisodeIcon(
					id = item.second,
					iconSize = IconSize.Medium,
					tint = null,
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun OtherIconsPreview() {
	LazyVerticalGrid(
		columns = GridCells.Adaptive(minSize = 100.dp),
		modifier = Modifier.padding(0.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {

		items(
			listOf(
				Pair("ic_arrow_back", R.drawable.ic_arrow_back),
				Pair("ic_arrow_back_ios", R.drawable.ic_arrow_back_ios),
				Pair("ic_arrow_forward_ios", R.drawable.ic_arrow_forward_ios),
				Pair("ic_arrow_drop_down", R.drawable.ic_arrow_drop_down),
				Pair("ic_clear", R.drawable.ic_clear),
				Pair("ic_add", R.drawable.ic_add),
				Pair("ic_edit", R.drawable.ic_edit),
				Pair("ic_location", R.drawable.ic_location),
				Pair("ic_settings", R.drawable.ic_settings),
				Pair("ic_calendar", R.drawable.ic_calendar),
				Pair("ic_group", R.drawable.ic_group),
				Pair("ic_eat", R.drawable.ic_eat),
				Pair("ic_see", R.drawable.ic_see),
				Pair("ic_other", R.drawable.ic_other),
			),
		) { item ->
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(4.dp),
			) {
				MapisodeText(
					text = item.first,
					style = MapisodeTheme.typography.labelMedium,
				)
				MapisodeIcon(
					id = item.second,
					iconSize = IconSize.Medium,
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun NavigationIconsPreview() {
	LazyVerticalGrid(
		columns = GridCells.Fixed(2),
		modifier = Modifier.padding(0.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {

		items(
			listOf(
				Pair("ic_house", R.drawable.ic_house),
				Pair("ic_edit_note", R.drawable.ic_edit_note),
				Pair("ic_groups_2", R.drawable.ic_groups_2),
				Pair("ic_account_circle", R.drawable.ic_account_circle),
			),
		) { item ->
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(4.dp),
			) {
				MapisodeText(
					text = item.first,
					style = MapisodeTheme.typography.labelMedium,
				)
				MapisodeIcon(
					id = item.second,
					iconSize = IconSize.Medium,
				)
			}
		}
	}
}
