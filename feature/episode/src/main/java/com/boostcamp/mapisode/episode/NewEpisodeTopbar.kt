package com.boostcamp.mapisode.episode

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar

@Composable
internal fun NewEpisodeTopbar(navController: NavController) {
	TopAppBar(
		title = stringResource(R.string.new_episode_menu_create_episode),
		navigationIcon = {
			MapisodeIconButton(
				onClick = {
					navController.navigateUp()
				},
			) {
				MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_back_ios)
			}
		},
		actions = {
			MapisodeIconButton(
				onClick = {
					navController.popBackStack("new_episode_pics", false)
				},
			) {
				MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_clear)
			}
		},
	)
}
