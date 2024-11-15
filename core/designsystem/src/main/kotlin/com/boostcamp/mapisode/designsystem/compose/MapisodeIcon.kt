package com.boostcamp.mapisode.designsystem.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
	tint: Color? = Color.Unspecified,
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
fun IconPreview() {
	Column(
		modifier = Modifier
			.padding(4.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		MapisodeText(text = "Extra Small Icon", style = MapisodeTheme.typography.labelMedium)
		MapisodeIcon(
			id = R.drawable.ic_account_circle_24,
			iconSize = IconSize.ExtraSmall,
			tint = MapisodeTheme.colorScheme.iconColor,
		)

		MapisodeText(text = "Small Icon", style = MapisodeTheme.typography.labelMedium)
		MapisodeIcon(
			id = R.drawable.ic_account_circle_24,
			iconSize = IconSize.Small,
			tint = MapisodeTheme.colorScheme.iconColor,
		)

		MapisodeText(text = "Medium Icon", style = MapisodeTheme.typography.labelMedium)
		MapisodeIcon(
			id = R.drawable.ic_account_circle_24,
			iconSize = IconSize.Medium,
			tint = MapisodeTheme.colorScheme.iconColor,
		)

		MapisodeText(text = "Large Icon", style = MapisodeTheme.typography.labelMedium)
		MapisodeIcon(
			id = R.drawable.ic_account_circle_24,
			iconSize = IconSize.Large,
			tint = MapisodeTheme.colorScheme.iconColor,
		)
	}
}

@Preview(showBackground = true)
@Composable
fun IconTintPreview() {
	Column(
		modifier = Modifier
			.padding(4.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		MapisodeText(text = "Eat Marker Light Icon", style = MapisodeTheme.typography.labelMedium)
		MapisodeIcon(
			id = R.drawable.ic_eat_marker_light,
			iconSize = IconSize.Medium,
			tint = null,
		)

		MapisodeText(text = "Eat Marker Dark Icon", style = MapisodeTheme.typography.labelMedium)
		MapisodeIcon(
			id = R.drawable.ic_eat_marker_dark,
			iconSize = IconSize.Medium,
			tint = null,
		)

		MapisodeText(text = "See Marker Light Icon", style = MapisodeTheme.typography.labelMedium)
		MapisodeIcon(
			id = R.drawable.ic_see_marker_light,
			iconSize = IconSize.Medium,
			tint = null,
		)

		MapisodeText(text = "See Marker Dark Icon", style = MapisodeTheme.typography.labelMedium)
		MapisodeIcon(
			id = R.drawable.ic_see_marker_dark,
			iconSize = IconSize.Medium,
			tint = null,
		)

		MapisodeText(text = "Other Marker Light Icon", style = MapisodeTheme.typography.labelMedium)
		MapisodeIcon(
			id = R.drawable.ic_other_marker_light,
			iconSize = IconSize.Medium,
			tint = null,
		)

		MapisodeText(text = "See Marker Dark Icon", style = MapisodeTheme.typography.labelMedium)
		MapisodeIcon(
			id = R.drawable.ic_other_marker_dark,
			iconSize = IconSize.Medium,
			tint = null,
		)
	}
}
