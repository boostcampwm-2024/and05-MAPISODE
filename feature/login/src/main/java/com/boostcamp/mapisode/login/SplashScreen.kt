package com.boostcamp.mapisode.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.boostcamp.mapisode.designsystem.R

@Composable
fun SplashScreen() {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_mapisode_logo_foreground),
			contentDescription = "",
			modifier = Modifier
				.fillMaxWidth(0.75f)
				.aspectRatio(1f),
			contentScale = ContentScale.Fit,
		)
	}
}
