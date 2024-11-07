package com.boostcamp.mapisode.episode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun EpisodeRoute() {
	EpisodeScreen()
}

@Composable
private fun EpisodeScreen() {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		Text(text = "EpisodeScreen", style = MaterialTheme.typography.displayMedium)
	}
}
