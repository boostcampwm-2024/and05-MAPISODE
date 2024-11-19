package com.boostcamp.mapisode.episode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.MapisodeTextField
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.textFieldModifier
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.textFieldVerticalArrangement

@Composable
internal fun NewEpisodeInfoScreen(navController: NavController) {
	MapisodeScaffold(
		topBar = {
			NewEpisodeTopbar(navController)
		},
		isStatusBarPaddingExist = true,
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
				.padding(20.dp),
			verticalArrangement = Arrangement.SpaceBetween,
		) {
			Column {
				Column(textFieldModifier, verticalArrangement = textFieldVerticalArrangement) {
					MapisodeText(
						text = stringResource(R.string.new_episode_info_location),
						style = MapisodeTheme.typography.labelLarge,
					)
					MapisodeTextField(
						modifier = Modifier.fillMaxWidth(),
						value = "",
						placeholder = stringResource(R.string.new_episode_info_placeholder_location),
						onValueChange = {},
						trailingIcon = {
							MapisodeIconButton(
								onClick = {
									navController.navigate("pick_location")
								},
							) { MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_location) }
						},
					)
				}
				Column(textFieldModifier, verticalArrangement = textFieldVerticalArrangement) {
					MapisodeText(
						text = stringResource(R.string.new_episode_info_group),
						style = MapisodeTheme.typography.labelLarge,
					)
					MapisodeTextField(
						modifier = Modifier.fillMaxWidth(),
						value = "",
						placeholder = stringResource(R.string.new_episode_info_placeholder_group),
						onValueChange = {},
						trailingIcon = {
							MapisodeIconButton(
								onClick = {
								},
							) { MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_drop_down) }
						},
					)
				}
				Column(textFieldModifier, verticalArrangement = textFieldVerticalArrangement) {
					MapisodeText(
						text = stringResource(R.string.new_episode_info_category),
						style = MapisodeTheme.typography.labelLarge,
					)
					MapisodeTextField(
						modifier = Modifier.fillMaxWidth(),
						value = "",
						placeholder = stringResource(R.string.new_episode_info_placeholder_category),
						onValueChange = {},
						trailingIcon = {
							MapisodeIconButton(
								onClick = {
								},
							) { MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_drop_down) }
						},
					)
				}
				Column(textFieldModifier, verticalArrangement = textFieldVerticalArrangement) {
					MapisodeText(
						text = stringResource(R.string.new_episode_info_tags),
						style = MapisodeTheme.typography.labelLarge,
					)
					MapisodeTextField(
						modifier = Modifier.fillMaxWidth(),
						value = "",
						placeholder = stringResource(R.string.new_episode_info_placeholder_tags),
						onValueChange = {},
					)
				}
				Column(textFieldModifier, verticalArrangement = textFieldVerticalArrangement) {
					MapisodeText(
						text = stringResource(R.string.new_episode_info_date),
						style = MapisodeTheme.typography.labelLarge,
					)
					MapisodeTextField(
						modifier = Modifier.fillMaxWidth(),
						value = "",
						placeholder = stringResource(R.string.new_episode_info_placeholder_date),
						onValueChange = {},
						trailingIcon = {
							MapisodeIconButton(
								onClick = {},
							) { MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_calendar) }
						},
					)
				}
			}
			MapisodeFilledButton(
				modifier = textFieldModifier,
				onClick = {
					navController.navigate("new_episode_content")
				},
				text = "다음",
			)
		}
	}
}

@Preview
@Composable
internal fun NewEpisodeInfoScreenPreview() {
	NewEpisodeInfoScreen(rememberNavController())
}
