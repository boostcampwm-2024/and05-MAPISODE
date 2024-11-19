package com.boostcamp.mapisode.episode

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.MapisodeTextField
import com.boostcamp.mapisode.designsystem.compose.Surface
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.textFieldModifier
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.textFieldVerticalArrangement

@Composable
internal fun NewEpisodeContentScreen(navController: NavController) {
	MapisodeScaffold(
		topBar = {
			NewEpisodeTopbar(navController)
		},
		isStatusBarPaddingExist = true,
	)
	{ innerPadding ->
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
						text = stringResource(R.string.new_episode_content_title),
						style = MapisodeTheme.typography.labelLarge,
					)
					MapisodeTextField(
						modifier = Modifier.fillMaxWidth(),
						value = "",
						placeholder = stringResource(R.string.new_episode_content_placeholder_title),
						onValueChange = {},
					)
				}
				Column(textFieldModifier, verticalArrangement = textFieldVerticalArrangement) {
					MapisodeText(
						text = stringResource(R.string.new_episode_content_description),
						style = MapisodeTheme.typography.labelLarge,
					)
					MapisodeTextField(
						modifier = Modifier
							.fillMaxWidth()
							.fillMaxHeight(0.3f),
						value = "",
						placeholder = stringResource(R.string.new_episode_content_placeholder_description),
						onValueChange = {},
					)
				}
				Column(textFieldModifier, verticalArrangement = textFieldVerticalArrangement) {
					MapisodeText(
						text = stringResource(R.string.new_episode_content_image),
						style = MapisodeTheme.typography.labelLarge,
					)
					LazyRow(
						contentPadding = PaddingValues(12.dp),
						horizontalArrangement = Arrangement.spacedBy(10.dp),
					) {
						items(3) {
							Surface(Modifier.size(150.dp)) {
								Image(
									contentDescription = null,
									imageVector = ImageVector.vectorResource(com.boostcamp.mapisode.designsystem.R.drawable.ic_see),
								)
							}
						}
					}
				}
			}
			MapisodeFilledButton(
				modifier = textFieldModifier,
				onClick = {
					navController.navigate("new_episode_content")
				},
				text = stringResource(R.string.new_episode_create_episode),
			)
		}
	}
}

@Preview
@Composable
internal fun NewEpisodeContentScreenPreview() {
	NewEpisodeContentScreen(rememberNavController())
}


