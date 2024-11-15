package com.boostcamp.mapisode.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.Surface
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.home.R

@Composable
fun MapisodeFabOverlayButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	iconId: Int = R.drawable.ic_group,
	backgroundColor: Color = MapisodeTheme.colorScheme.fabBackground,
	iconTint: Color = MapisodeTheme.colorScheme.fabContent,
) {
	Surface(
		modifier = modifier
			.clip(RoundedCornerShape(16.dp))
			.clickable(onClick = onClick),
		shape = RoundedCornerShape(16.dp),
		color = backgroundColor,
	) {
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier.padding(7.dp),
		) {
			MapisodeIcon(
				id = iconId,
				iconSize = IconSize.Normal,
				tint = iconTint,
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun MapisodeFabOverlayButtonPreview() {
	MapisodeFabOverlayButton(onClick = {})
}
