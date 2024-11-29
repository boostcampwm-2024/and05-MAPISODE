package com.boostcamp.mapisode.ui.photopicker.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.boostcamp.mapisode.designsystem.compose.Surface
import com.boostcamp.mapisode.ui.photopicker.model.PhotoInfo

@Composable
fun PhotoItem(
	photo: PhotoInfo,
	checked: Boolean,
	onPhotoClick: () -> Unit,
	modifier: Modifier = Modifier,
) {
	Surface(
		modifier = modifier
			.aspectRatio(1f)
			.clickable(onClick = onPhotoClick),
	) {
		Box(modifier = Modifier.fillMaxSize()) {
			Image(
				painter = rememberAsyncImagePainter(
					ImageRequest.Builder(LocalContext.current).data(data = photo.uri).build(),
				),
				contentDescription = "Photo",
				contentScale = ContentScale.Crop,
				modifier = Modifier.fillMaxSize(),
			)
			CircleWithCheck(
				checked = checked,
				modifier = Modifier
					.align(Alignment.TopEnd)
					.padding(4.dp),
			)
		}
	}
}
