package com.boostcamp.mapisode.episode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.Surface
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.textFieldModifier
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.textFieldVerticalArrangement
import com.boostcamp.mapisode.episode.intent.NewEpisodeContent
import com.boostcamp.mapisode.episode.intent.NewEpisodeState

@Composable
internal fun NewEpisodeContentScreen(
	state: NewEpisodeState,
	navController: NavController,
	submitEpisode: (NewEpisodeContent) -> Unit = {},
) {
	var titleValue by remember { mutableStateOf(state.episodeContent.title) }
	var descriptionValue by remember { mutableStateOf(state.episodeContent.description) }

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
				EpisodeTextFieldGroup(
					labelRes = R.string.new_episode_content_title,
					placeholderRes = R.string.new_episode_content_placeholder_title,
					value = titleValue,
					onValueChange = { titleValue = it },
				)
				EpisodeTextFieldGroup(
					modifier = Modifier.fillMaxHeight(0.3f),
					labelRes = R.string.new_episode_content_description,
					placeholderRes = R.string.new_episode_content_placeholder_description,
					value = descriptionValue,
					onValueChange = { descriptionValue = it },
				)
				Column(textFieldModifier, verticalArrangement = textFieldVerticalArrangement) {
					MapisodeText(
						text = stringResource(R.string.new_episode_content_image),
						style = MapisodeTheme.typography.labelLarge,
					)
					LazyRow(
						contentPadding = PaddingValues(12.dp),
						horizontalArrangement = Arrangement.spacedBy(10.dp),
					) {
						items(state.episodeContent.images) { imageUri ->
							Surface(Modifier.size(150.dp)) {
								AsyncImage(
									model = imageUri,
									contentDescription = null,
								)
							}
						}
					}
				}
			}
			MapisodeFilledButton(
				modifier = textFieldModifier,
				onClick = {
					submitEpisode(
						NewEpisodeContent(
							titleValue,
							descriptionValue,
							state.episodeContent.images,
						),
					)
					navController.popBackStack("new_episode_pics", inclusive = false)
				},
				text = stringResource(R.string.new_episode_create_episode),
			)
		}
	}
}

@Preview
@Composable
internal fun NewEpisodeContentScreenPreview() {
	NewEpisodeContentScreen(
		state = NewEpisodeState(),
		navController = rememberNavController(),
	)
}
