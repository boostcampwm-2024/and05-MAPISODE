package com.boostcamp.mapisode.mygroup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
internal fun GroupRoute() {
	GroupScreen()
}

@Composable
private fun GroupScreen() {
	MapisodeScaffold(
		modifier = Modifier
			.fillMaxSize(),
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = "나의 그룹",
			)
		},
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(it),
			contentAlignment = Alignment.Center,
		) {
			MapisodeText(
				text = "Group",
				style = MapisodeTheme.typography.displayMedium,
			)
		}
	}
}
