package com.boostcamp.mapisode.episode.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object NewEpisodeConstant {
	const val MAP_DEFAULT_ZOOM = 16.0

	val textFieldModifier = Modifier
		.fillMaxWidth()
		.padding(vertical = 15.dp)
	val textFieldVerticalArrangement = Arrangement.spacedBy(12.dp)
	val groupMap = mapOf("나의 에피소드" to "my_episode")
	val categoryMap = mapOf("먹을 것" to "EAT", "볼 것" to "SEE", "나머지" to "OTHER")
}
