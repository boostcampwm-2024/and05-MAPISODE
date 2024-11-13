package com.boostcamp.mapisode.mygroup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
internal fun GroupRoute() {
	GroupScreen()
}

@Composable
private fun GroupScreen() {
	MapisodeScaffold(
		modifier = Modifier
			.fillMaxSize()
			.offset(y = -WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
	) {
		Box(
			modifier = Modifier.fillMaxSize().padding(it),
			contentAlignment = Alignment.Center,
		) {
			MapisodeText(
				text = "Group",
				style = MapisodeTheme.typography.displayMedium,
			)
		}
	}
}
