package com.boostcamp.mapisode.mygroup.screen

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
import com.boostcamp.mapisode.designsystem.compose.card.GroupCard
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenu
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenuItem
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.mygroup.intent.GroupIntent
import com.boostcamp.mapisode.mygroup.sideeffect.GroupSideEffect
import com.boostcamp.mapisode.mygroup.sideeffect.rememberFlowWithLifecycle
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
	val context = LocalContext.current
	val uiState = viewModel.uiState.collectAsStateWithLifecycle()
	val effect = rememberFlowWithLifecycle(
		flow = viewModel.sideEffect,
		initialValue = GroupSideEffect.Idle,
	).value

	LaunchedEffect(effect) {
		when (effect) {
			is GroupSideEffect.ShowToast -> {
				Toast.makeText(context, effect.messageResId, Toast.LENGTH_SHORT).show()
			}

			is GroupSideEffect.NavigateToGroupJoinScreen -> {
				onGroupJoinClick()
			}

			is GroupSideEffect.NavigateToGroupCreateScreen -> {
				onGroupCreationClick()
			}

			is GroupSideEffect.NavigateToGroupDetailScreen -> {
				onGroupDetailClick(effect.groupId)
			}

			else -> {}
		}
	}

	LaunchedEffect(uiState.value) {
		if (uiState.value.isInitializing) {
			viewModel.onIntent(GroupIntent.LoadGroups)
		}
	}

	GroupScreen(
		onGroupJoinClick = {
			viewModel.onIntent(GroupIntent.OnJoinClick)
		},
		onGroupDetailClick = { groupId ->
			viewModel.onIntent(GroupIntent.OnGroupDetailClick(groupId))
		},
		onGroupCreationClick = {
			viewModel.onIntent(GroupIntent.OnGroupCreateClick)
		},
		uiState = uiState,
	)
}

@Composable
private fun <T> GroupScreen(
	onGroupJoinClick: () -> Unit,
	onGroupDetailClick: (String) -> Unit,
	onGroupCreationClick: () -> Unit,
	uiState: State<T>,
) {
	val focusManager = LocalFocusManager.current
	var isMenuPoppedUp by remember { mutableStateOf(false) }

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
