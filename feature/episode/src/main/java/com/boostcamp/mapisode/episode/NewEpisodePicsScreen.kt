package com.boostcamp.mapisode.episode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
internal fun NewEpisodePicsScreen(navController: NavController) {
	MapisodeScaffold(
		topBar = {
			TopAppBar(
				title = stringResource(R.string.new_episode_menu_title),
				actions = {
					MapisodeIconButton(
						onClick = {
							navController.navigate("new_episode_info")
						},
					) {
						MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_forward_ios)
					}
				},
			)
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
				text = "사진 선택 화면",
				style = MapisodeTheme.typography.displayLarge,
			)
		}
	}
}
