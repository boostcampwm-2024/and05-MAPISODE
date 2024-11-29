package com.boostcamp.mapisode.ui.photopicker.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.Surface
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun CameraItem(
	onCameraClick: () -> Unit,
	modifier: Modifier = Modifier,
) {
	Surface(
		modifier = modifier
			.aspectRatio(1f)
			.clickable(onClick = onCameraClick),
		contentColor = MapisodeTheme.colorScheme.fabContent,
	) {
		Box(
			contentAlignment = Alignment.Center,
		) {
			MapisodeIcon(
				id = R.drawable.ic_camera,
				iconSize = IconSize.ExtraLarge,
			)
		}
	}
}
