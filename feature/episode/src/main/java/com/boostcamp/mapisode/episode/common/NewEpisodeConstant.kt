package com.boostcamp.mapisode.episode.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object NewEpisodeConstant {
	const val MAP_DEFAULT_ZOOM = 16.0
	const val MAX_PHOTO_COUNTS = 4

	val textFieldModifier = Modifier
		.fillMaxWidth()
		.padding(vertical = 15.dp)
	val buttonModifier = Modifier
		.fillMaxWidth()
		.height(64.dp)
	val textFieldVerticalArrangement = Arrangement.spacedBy(12.dp)
}
