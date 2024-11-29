package com.boostcamp.mapisode.ui.photopicker.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun CircleWithCheck(
	checked: Boolean,
	modifier: Modifier = Modifier,
) {
	Box(
		modifier = modifier
			.padding(4.dp)
			.size(28.dp)
			.background(
				color = MapisodeTheme.colorScheme.checkboxBackground,
				shape = CircleShape,
			)
			.border(
				width = 1.dp,
				color = MapisodeTheme.colorScheme.checkboxStroke,
				shape = CircleShape,
			),
		contentAlignment = Alignment.Center,
	) {
		if (checked) {
			MapisodeIcon(id = com.boostcamp.mapisode.designsystem.R.drawable.ic_check)
		}
	}
}
