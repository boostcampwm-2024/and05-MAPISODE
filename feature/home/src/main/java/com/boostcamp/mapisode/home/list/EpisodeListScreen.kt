package com.boostcamp.mapisode.home.list

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
import com.boostcamp.mapisode.designsystem.compose.MapisodeCircularLoadingIndicator

@Composable
fun EpisodeListRoute(
	groupId: String,
	viewModel: EpisodeListViewModel = hiltViewModel(),
	onBackClick: () -> Unit,
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val context = LocalContext.current

	LaunchedEffect(Unit) {
		viewModel.onIntent(EpisodeListIntent.LoadEpisodeList(groupId))
	}

	LaunchedEffect(Unit) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is EpisodeListSideEffect.ShowToast -> {
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
		EpisodeListScreen(state = uiState, onBackClick = onBackClick)
	}
}

@Composable
fun EpisodeListScreen(
	state: EpisodeListState,
	onBackClick: () -> Unit,
) {

}
