package com.boostcamp.mapisode.home.detail

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeCircularLoadingIndicator
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.home.common.EpisodeDetailPreviewProvider

@Composable
internal fun EpisodeDetailRoute(
	episodeId: String,
	viewModel: EpisodeDetailViewModel = hiltViewModel(),
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
		EpisodeDetailScreen(state = uiState)
	}
}

@Composable
internal fun EpisodeDetailScreen(
	state: EpisodeDetailState,
	modifier: Modifier = Modifier,
) {
	MapisodeScaffold(
		modifier = Modifier.fillMaxSize(),
		isStatusBarPaddingExist = true,
		isNavigationBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = state.episode.title,
				navigationIcon = {
					MapisodeIconButton(
						onClick = {},
					) {
						MapisodeIcon(
							id = R.drawable.ic_arrow_back_ios,
							iconSize = IconSize.ExtraSmall,
						)
					}
				},
				actions = {
					MapisodeIconButton(
						onClick = {},
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
					horizontalArrangement = Arrangement.spacedBy(4.dp),
					verticalAlignment = Alignment.CenterVertically,
				) {
					imageUrls.forEach { imageUrl ->
						AsyncImage(
							model = imageUrl,
							contentDescription = "애피소드 이미지",
							modifier = Modifier
								.size(110.dp)
								.clip(RoundedCornerShape(16.dp)),
						)
					}
				}
			}
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
		EpisodeDetailScreen(state = state)
	}
}
