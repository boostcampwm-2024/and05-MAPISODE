package com.boostcamp.mapisode.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.test.FakeImage
import com.boostcamp.mapisode.common.util.toFormattedString
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.theme.AppTypography
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.home.R
import com.boostcamp.mapisode.model.EpisodeModel
import java.util.Date

@Composable
fun EpisodeCard(
	episode: EpisodeModel,
	modifier: Modifier = Modifier,
	onClick: (String) -> Unit = {},
) {
	val tagString = episode.tags.joinToString(
		prefix = stringResource(R.string.episode_card_tag_prefix),
		separator = ", #",
	)

	Row(
		modifier = modifier
			.fillMaxWidth()
			.clip(RoundedCornerShape(12.dp))
			.background(
				color = MapisodeTheme.colorScheme.surfaceBackground,
				shape = RoundedCornerShape(12.dp),
			)
			.padding(20.dp),
	) {
		AsyncImage(
			model = episode.imageUrls.firstOrNull(),
			contentDescription = stringResource(R.string.episode_card_image_description),
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.clip(RoundedCornerShape(8.dp))
				.size(120.dp),
		)

		Spacer(modifier = Modifier.width(17.dp))

		Column(
			modifier = Modifier
				.weight(1f)
				.height(120.dp),
		) {
			MapisodeText(
				text = episode.title,
				style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Medium),
				color = MapisodeTheme.colorScheme.textContent,
				maxLines = 1,
			)

			Spacer(modifier = Modifier.height(2.dp))

			MapisodeText(
				text = stringResource(
					R.string.episode_card_date_prefix,
					episode.createdAt.toFormattedString(),
				),
				style = AppTypography.labelMedium.copy(fontSize = 10.dp),
				color = MapisodeTheme.colorScheme.textContent,
				maxLines = 1,
			)

			Spacer(modifier = Modifier.height(2.dp))

			MapisodeText(
				text = stringResource(R.string.episode_creator_tag_prefix, episode.createdBy),
				style = AppTypography.labelMedium.copy(fontSize = 10.dp),
				color = MapisodeTheme.colorScheme.textContent,
				maxLines = 1,
			)

			Spacer(modifier = Modifier.height(2.dp))

			MapisodeText(
				text = stringResource(R.string.episode_card_category_prefix, episode.category),
				style = AppTypography.labelMedium.copy(fontSize = 10.dp),
				color = MapisodeTheme.colorScheme.textContent,
				maxLines = 1,
			)

			Spacer(modifier = Modifier.height(2.dp))

			if (episode.tags.isNotEmpty()) {
				MapisodeText(
					text = tagString,
					style = AppTypography.labelMedium.copy(fontSize = 10.dp),
					color = MapisodeTheme.colorScheme.textContent,
					maxLines = 1,
				)
			}

			Spacer(modifier = Modifier.weight(1f))

			Row(
				modifier = Modifier.fillMaxWidth(),
			) {
				Spacer(modifier = Modifier.weight(1f))

				MapisodeFilledButton(
					text = stringResource(R.string.episode_card_detail),
					onClick = { onClick(episode.id) },
					textStyle = MapisodeTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
				)
			}
		}
	}
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true)
@Composable
fun EpisodeCardPreview() {
	val previewHandler = AsyncImagePreviewHandler {
		FakeImage(color = 0xFFE0E0E0.toInt())
	}

	CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
		EpisodeCard(
			episode = EpisodeModel(
				imageUrls = listOf("https://avatars.githubusercontent.com/u/98825364?v=4?s=100"),
				title = "Title",
				createdAt = Date(),
				createdBy = "AndroidDessertClub",
				category = "Category",
				tags = listOf("tag1", "tag2"),
			),
		)
	}
}
