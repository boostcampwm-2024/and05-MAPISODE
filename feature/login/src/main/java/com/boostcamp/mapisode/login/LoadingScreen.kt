package com.boostcamp.mapisode.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.boostcamp.mapisode.designsystem.compose.MapisodeCircularLoadingIndicator
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Spacer(modifier = Modifier.weight(1f))

			MapisodeIcon(
				id = com.boostcamp.mapisode.designsystem.R.drawable.ic_mapisode_brand_text,
				modifier = Modifier
					.fillMaxWidth(0.45f)
					.aspectRatio(2.8f),
				tint = null,
			)

			Image(
				painter = painterResource(id = com.boostcamp.mapisode.designsystem.R.drawable.ic_mapisode_sublogo_foreground),
				contentDescription = stringResource(R.string.login_mapisode_subicon),
				modifier = Modifier
					.fillMaxWidth()
					.aspectRatio(1.5f),
				contentScale = ContentScale.Fit,
			)

			MapisodeCircularLoadingIndicator()

			Spacer(modifier = Modifier.weight(1f))
		}
	}
}
