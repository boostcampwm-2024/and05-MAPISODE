package com.boostcamp.mapisode.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun HomeRoute(
	viewModel: HomeViewModel = hiltViewModel(),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val snackbarHostState = remember { SnackbarHostState() }
	val context = LocalContext.current

	LaunchedEffect(viewModel) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is HomeSideEffect.ShowToast -> {
					snackbarHostState.showSnackbar(sideEffect.message)
				}
			}
		}
	}

	HomeScreen(
		state = uiState,
		onIncrement = { viewModel.onIntent(HomeIntent.Increment) },
		onDecrement = { viewModel.onIntent(HomeIntent.Decrement) },
		snackbarHostState = snackbarHostState,
	)
}

@Composable
private fun HomeScreen(
	state: HomeState,
	onIncrement: () -> Unit,
	onDecrement: () -> Unit,
	snackbarHostState: SnackbarHostState,
) {
	Scaffold(
		snackbarHost = { SnackbarHost(snackbarHostState) },
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(it),
			contentAlignment = Alignment.Center,
		) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Text(
					text = "Count: ${state.count}",
					style = MaterialTheme.typography.displayMedium,
				)
				Spacer(modifier = Modifier.height(16.dp))
				Row {
					Button(
						onClick = onDecrement,
						modifier = Modifier.padding(8.dp),
					) {
						Text(text = "-")
					}
					Button(
						onClick = onIncrement,
						modifier = Modifier.padding(8.dp),
					) {
						Text(text = "+")
					}
				}
			}
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
	HomeScreen(
		state = HomeState(count = 42),
		onIncrement = {},
		onDecrement = {},
		snackbarHostState = SnackbarHostState(),
	)
}
