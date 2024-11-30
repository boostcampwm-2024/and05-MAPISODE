package com.boostcamp.mapisode.home.list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeCircularLoadingIndicator
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenu
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenuItem
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.home.common.SortOption
import com.boostcamp.mapisode.home.component.EpisodeListCard
import okhttp3.internal.toImmutableList

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
		EpisodeListScreen(
			state = uiState,
			onBackClick = onBackClick,
			onSortOptionChange = { sortOption ->
				viewModel.onIntent(EpisodeListIntent.ChangeSortOption(sortOption))
			},
		)
	}
}

@Composable
fun EpisodeListScreen(
	state: EpisodeListState,
	onBackClick: () -> Unit,
	onSortOptionChange: (SortOption) -> Unit = {},
) {
	var expanded by rememberSaveable { mutableStateOf(false) }
	val sortOptions = SortOption.entries.toImmutableList()


	MapisodeScaffold(
		isStatusBarPaddingExist = true,
		isNavigationBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = "", // TODO 그룹 이름 가져오기,
				navigationIcon = {
					MapisodeIconButton(
						onClick = { onBackClick() },
					) {
						MapisodeIcon(
							id = R.drawable.ic_arrow_back_ios,
						)
					}
				},
				actions = {},
			)
		},
	) {
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.padding(it),
			verticalArrangement = Arrangement.spacedBy(10.dp),
			contentPadding = PaddingValues(vertical = 10.dp, horizontal = 20.dp),
		) {
			item {
				Row(
					modifier = Modifier.fillParentMaxWidth(),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.SpaceBetween,
				) {
					MapisodeText(
						text = "에피소드",
						style = MapisodeTheme.typography.labelLarge,
					)
					MapisodeIconButton(
						onClick = { expanded = true },
					) {
						Row(
							verticalAlignment = Alignment.CenterVertically,
						) {
							MapisodeText(
								text = stringResource(state.selectedSortOption.label),
							)
							MapisodeIcon(
								id = R.drawable.ic_arrow_drop_down,
							)
						}

						MapisodeDropdownMenu(
							expanded = expanded,
							onDismissRequest = { expanded = false },
							modifier = Modifier.wrapContentWidth(),
							offset = DpOffset(0.dp, 0.dp).minus(DpOffset(16.dp, 16.dp)),
						) {
							sortOptions.forEach { option ->
								MapisodeDropdownMenuItem(
									onClick = {
										onSortOptionChange(option)
										expanded = false
									},
								) {
									MapisodeText(
										text = stringResource(option.label),
									)
								}
							}
						}
					}
				}
			}
			items(
				items = state.episodes,
				key = { episode -> episode.id },
			) { episode ->
				EpisodeListCard(
					imageUrl = episode.imageUrls.first(),
					title = episode.title,
					createdBy = episode.createdBy,
					address = episode.address,
					createdAt = episode.createdAt,
					content = episode.content,
				)
			}
		}
	}
}
