package com.boostcamp.mapisode.mygroup.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenu
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenuItem
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.mygroup.component.GroupCard
import com.boostcamp.mapisode.mygroup.intent.GroupIntent
import com.boostcamp.mapisode.mygroup.viewmodel.GroupViewModel

@Composable
internal fun MainGroupRoute(
	onGroupJoinClick: () -> Unit,
	onGroupDetailClick: () -> Unit,
	onGroupCreationClick: () -> Unit,
	viewModel: GroupViewModel = hiltViewModel(),
) {
	GroupScreen(
		onGroupJoinClick = onGroupJoinClick,
		onGroupDetailClick = onGroupDetailClick,
		onGroupCreationClick = onGroupCreationClick,
		viewModel = viewModel,
	)
}

@Composable
private fun GroupScreen(
	onGroupJoinClick: () -> Unit,
	onGroupDetailClick: () -> Unit,
	onGroupCreationClick: () -> Unit,
	viewModel: GroupViewModel,
) {
	val uiState = viewModel.uiState.collectAsStateWithLifecycle()
	val focusManager = LocalFocusManager.current
	var isMenuPoppedUp by remember { mutableStateOf(false) }

	LaunchedEffect(uiState) {
		if (uiState.value.areGroupsLoading && !uiState.value.areGroupsVisible) {
			if (uiState.value.groups.isEmpty()) {
				viewModel.onIntent(GroupIntent.LoadGroups)
			} else {
				viewModel.confirmGroupsLoaded()
			}
		} else {
			viewModel.onIntent(GroupIntent.LoadGroups)
		}
	}

	MapisodeScaffold(
		modifier = Modifier
			.fillMaxSize()
			.pointerInput(Unit) {
				detectTapGestures(
					onPress = {
						focusManager.clearFocus()
					},
				)
			},
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = "나의 그룹",
				actions = {
					MapisodeIconButton(
						onClick = {
							isMenuPoppedUp = true
						},
					) {
						MapisodeIcon(
							id = R.drawable.ic_add,
						)
						MapisodeDropdownMenu(
							expanded = isMenuPoppedUp,
							onDismissRequest = { isMenuPoppedUp = false },
							offset = DpOffset(0.dp, 0.dp).minus(DpOffset(41.dp, 0.dp)),
						) {
							MapisodeDropdownMenuItem(
								onClick = { onGroupJoinClick() },
							) {
								MapisodeText(
									text = "그룹 참여",
								)
							}
							MapisodeDropdownMenuItem(
								onClick = { onGroupCreationClick() },
							) {
								MapisodeText(
									text = "그룹 생성",
								)
							}
						}
					}
				},
			)
		},
	) {
		LazyVerticalGrid(
			modifier = Modifier
				.padding(it),
			columns = GridCells.Fixed(2),
			contentPadding = PaddingValues(
				start = 30.dp,
				end = 30.dp,
			),
		) {
			uiState.value.groups.forEach { group ->
				item {
					GroupCard(
						onGroupDetailClick = onGroupDetailClick,
						imageUrl = group.imageUrl,
						title = group.name,
						content = group.users.size.toString(),
					)
				}
			}
		}
	}
}
