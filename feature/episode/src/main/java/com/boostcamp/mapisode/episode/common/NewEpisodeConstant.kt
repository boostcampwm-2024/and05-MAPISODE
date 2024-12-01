package com.boostcamp.mapisode.episode.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object NewEpisodeConstant {
	const val MAP_DEFAULT_ZOOM = 16.0

	val textFieldModifier = Modifier
		.fillMaxWidth()
		.padding(vertical = 15.dp)
	val buttonModifier = Modifier
		.fillMaxWidth()
		.fillMaxHeight(0.3f)
	val textFieldVerticalArrangement = Arrangement.spacedBy(12.dp)

	private const val CATEGORY_NAME_EAT = "먹을 것"
	private const val CATEGORY_NAME_SEE = "볼 것"
	private const val CATEGORY_NAME_OTHER = "나머지"
	private const val CATEGORY_VALUE_EAT = "EAT"
	private const val CATEGORY_VALUE_SEE = "SEE"
	private const val CATEGORY_VALUE_OTHER = "OTHER"
	val categoryMap = mapOf(
		CATEGORY_NAME_EAT to CATEGORY_VALUE_EAT,
		CATEGORY_NAME_SEE to CATEGORY_VALUE_SEE,
		CATEGORY_NAME_OTHER to CATEGORY_VALUE_OTHER,
	)
}
