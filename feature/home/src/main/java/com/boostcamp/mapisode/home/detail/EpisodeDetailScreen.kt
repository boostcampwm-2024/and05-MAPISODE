package com.boostcamp.mapisode.home.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeCircularLoadingIndicator
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar

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
		MapisodeText(text = "EpisodeDetailScreen")
	}
}
