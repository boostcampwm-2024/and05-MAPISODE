package com.boostcamp.mapisode.mygroup.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
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
import com.boostcamp.mapisode.designsystem.compose.card.GroupCard
import com.boostcamp.mapisode.mygroup.intent.GroupIntent
import com.boostcamp.mapisode.mygroup.state.GroupState
import com.boostcamp.mapisode.mygroup.viewmodel.GroupViewModel
import com.boostcamp.mapisode.mygroup.R as S

@Composable
internal fun MainGroupRoute(
	onGroupJoinClick: () -> Unit,
	onGroupDetailClick: (String) -> Unit,
	onGroupCreationClick: () -> Unit,
	viewModel: GroupViewModel = hiltViewModel(),
) {
	val uiState = viewModel.uiState.collectAsStateWithLifecycle()
	GroupScreen(
		onGroupJoinClick = onGroupJoinClick,
		onGroupDetailClick = onGroupDetailClick,
		onGroupCreationClick = onGroupCreationClick,
		uiState = uiState,
		onIntent = { viewModel.onIntent(it) },
	)
}

@Composable
private fun <T> GroupScreen(
	onGroupJoinClick: () -> Unit,
	onGroupDetailClick: (String) -> Unit,
	onGroupCreationClick: () -> Unit,
	uiState: State<T>,
	onIntent: (GroupIntent) -> Unit,
) {
	val focusManager = LocalFocusManager.current
	var isMenuPoppedUp by remember { mutableStateOf(false) }

	LaunchedEffect(uiState.value) {
		if (uiState.value is GroupState) {
			val groupState = uiState.value as GroupState
			if (groupState.groups.isEmpty()) {
				onIntent(GroupIntent.LoadGroups)
			}
			if (groupState.areGroupsLoading && groupState.groups.isNotEmpty()) {
				onIntent(GroupIntent.EndLoadingGroups)
			}
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
			contentPadding = PaddingValues(horizontal = 30.dp),
		) {
			if (uiState.value is GroupState) {
				val groupState = uiState.value as GroupState
				groupState.groups.forEach { group ->
					item {
						GroupCard(
							onGroupDetailClick = onGroupDetailClick,
							groupId = group.id,
							imageUrl = group.imageUrl,
							title = group.name,
							content = stringResource(S.string.group_members_number) + group.members.size.toString(),
						)
					}
				}
			}
		}
	}
}
