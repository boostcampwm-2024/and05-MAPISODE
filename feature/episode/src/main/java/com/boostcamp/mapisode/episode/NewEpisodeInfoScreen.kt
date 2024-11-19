package com.boostcamp.mapisode.episode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
internal fun NewEpisodeInfoScreen(navController: NavController) {
	MapisodeScaffold(
		topBar = {
			NewEpisodeTopbar(navController)
		},
		isStatusBarPaddingExist = true,
	)
	{ innerPadding ->
		Box(
			Modifier
				.fillMaxSize()
				.padding(innerPadding),
			contentAlignment = Alignment.Center,
		) {
			MapisodeText(
				text = "정보 입력 화면",
				style = MapisodeTheme.typography.displayLarge,
			)
		}
	}
}
