package com.boostcamp.mapisode.home.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.test.FakeImage
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.Direction
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeCircularLoadingIndicator
import com.boostcamp.mapisode.designsystem.compose.MapisodeDivider
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.Thickness
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeOutlinedButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.AppTypography
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.home.common.ChipType
import com.boostcamp.mapisode.home.common.EpisodeDetailPreviewProvider
import com.boostcamp.mapisode.home.common.getChipIconTint
import com.boostcamp.mapisode.home.common.mapCategoryToChipType
import com.boostcamp.mapisode.home.component.MapisodeChip

@Composable
internal fun EpisodeDetailRoute(
	episodeId: String,
	viewModel: EpisodeDetailViewModel = hiltViewModel(),
	onEpisodeEditClick: (String) -> Unit,
	onBackClick: () -> Unit = {},
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val context = LocalContext.current

	LaunchedEffect(Unit) {
		viewModel.onIntent(EpisodeDetailIntent.LoadEpisodeDetail(episodeId))
	}

	LaunchedEffect(Unit) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is EpisodeDetailSideEffect.ShowToast -> {
					val message = context.getString(sideEffect.messageResId)
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
				}
			}
		}
	}

	if (uiState.isLoading) {
		Box(
			modifier = Modifier.fillMaxSize(),
			contentAlignment = Alignment.Center,
		) {
			MapisodeCircularLoadingIndicator()
		}
	} else {
		EpisodeDetailScreen(
			state = uiState,
			onEpisodeEditClick = { onEpisodeEditClick(episodeId) },
			onBackClick = onBackClick,
		)
	}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun EpisodeDetailScreen(
	state: EpisodeDetailState,
	modifier: Modifier = Modifier,
	onEpisodeEditClick: () -> Unit,
	onBackClick: () -> Unit = {},
) {
	val context = LocalContext.current

	MapisodeScaffold(
		modifier = Modifier.fillMaxSize(),
		isStatusBarPaddingExist = true,
		isNavigationBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = state.episode.title,
				navigationIcon = {
					MapisodeIconButton(
						onClick = onBackClick,
					) {
						MapisodeIcon(
							id = R.drawable.ic_arrow_back_ios,
							iconSize = IconSize.ExtraSmall,
						)
					}
				},
				actions = {
					MapisodeIconButton(
						onClick = { onEpisodeEditClick() },
					) {
						MapisodeIcon(
							id = R.drawable.ic_edit,
						)
					}
				},
			)
		},
	) {
		Column(
			modifier = modifier
				.fillMaxSize()
				.padding(it),
		) {
			val imageUrls = state.episode.imageUrls

			Box(
				modifier = Modifier.fillMaxWidth(),
				contentAlignment = Alignment.Center,
			) {
				Row(
					modifier = Modifier
						.wrapContentWidth()
						.horizontalScroll(rememberScrollState()),
					horizontalArrangement = Arrangement.spacedBy(10.dp),
					verticalAlignment = Alignment.CenterVertically,
				) {
					imageUrls.forEach { imageUrl ->
						AsyncImage(
							model = imageUrl,
							contentDescription = "애피소드 이미지",
							modifier = Modifier
								.size(110.dp)
								.clip(RoundedCornerShape(16.dp)),
							contentScale = ContentScale.Crop,
						)
					}
				}
			}

			Spacer(modifier = Modifier.height(31.dp))

			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 20.dp)
					.clip(RoundedCornerShape(12.dp))
					.background(color = Color.White, shape = RoundedCornerShape(12.dp))
					.border(
						width = 1.dp,
						color = Color.Black,
						shape = RoundedCornerShape(12.dp),
					),
			) {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(16.dp),
				) {
					AsyncImage(
						model = state.author?.profileUrl,
						contentScale = ContentScale.Crop,
						contentDescription = "유저 프로필 이미지",
						modifier = Modifier
							.size(40.dp)
							.clip(CircleShape),
					)

					Spacer(modifier = Modifier.width(16.dp))

					Column(
						modifier = Modifier.weight(1f),
					) {
						MapisodeText(
							text = state.author?.name ?: "",
							style = AppTypography.titleMedium,
							color = MapisodeTheme.colorScheme.textContent,
						)

						Spacer(modifier = Modifier.height(4.dp))

						MapisodeText(
							text = state.episode.address,
							style = AppTypography.labelMedium,
							color = MapisodeTheme.colorScheme.textContent,
						)
					}
				}

				MapisodeDivider(
					direction = Direction.Horizontal,
					thickness = Thickness.Thin,
					color = Color.Black,
				)

				Spacer(modifier = Modifier.height(10.dp))

				FlowRow(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 20.dp),
					itemVerticalAlignment = Alignment.CenterVertically,
				) {
					val chipType = mapCategoryToChipType(state.episode.category) ?: ChipType.OTHER

					MapisodeChip(
						text = context.getString(chipType.textResId),
						iconId = chipType.iconResId,
						iconTint = getChipIconTint(chipType),
						isSelected = true,
					)

					Spacer(modifier = Modifier.width(6.dp))

					state.episode.tags.forEach { tag ->
						MapisodeText(
							text = "# $tag",
							style = AppTypography.labelMedium,
							color = MapisodeTheme.colorScheme.textContent,
							modifier = Modifier
								.padding(vertical = 4.dp)
								.background(
									color = MapisodeTheme.colorScheme.chipSelectedStroke,
									shape = RoundedCornerShape(6.dp),
								)
								.padding(6.dp),
						)

						Spacer(modifier = Modifier.width(6.dp))
					}
				}

				Spacer(modifier = Modifier.height(6.dp))

				if (state.episode.content.isNotBlank()) {
					MapisodeText(
						text = state.episode.content,
						style = AppTypography.labelMedium,
						color = MapisodeTheme.colorScheme.textFieldContent,
						modifier = Modifier
							.fillMaxWidth()
							.padding(horizontal = 20.dp)
							.clip(RoundedCornerShape(8.dp))
							.border(
								width = 1.dp,
								color = Color.Black,
								shape = RoundedCornerShape(8.dp),
							)
							.padding(vertical = 12.dp, horizontal = 10.dp),
					)
				}

				Spacer(modifier = Modifier.height(40.dp))
			}

			Spacer(modifier = Modifier.weight(1f))

			MapisodeOutlinedButton(
				text = "삭제하기",
				onClick = {},
				modifier = Modifier
					.fillMaxWidth()
					.padding(20.dp),
			)
		}
	}
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EpisodeDetailScreenPreview(
	@PreviewParameter(EpisodeDetailPreviewProvider::class) state: EpisodeDetailState,
) {
	val previewHandler = AsyncImagePreviewHandler {
		FakeImage(color = 0xFFE0E0E0.toInt())
	}

	CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
		EpisodeDetailScreen(state = state, onEpisodeEditClick = {})
	}
}
