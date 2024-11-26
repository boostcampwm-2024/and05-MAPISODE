package com.boostcamp.mapisode.mygroup.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.tab.MapisodeTab
import com.boostcamp.mapisode.designsystem.compose.tab.MapisodeTabRow
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.mygroup.intent.GroupDetailIntent
import com.boostcamp.mapisode.mygroup.sideeffect.GroupDetailSideEffect
import com.boostcamp.mapisode.mygroup.sideeffect.rememberFlowWithLifecycle
import com.boostcamp.mapisode.mygroup.state.GroupDetailState
import com.boostcamp.mapisode.mygroup.viewmodel.GroupDetailViewModel
import com.boostcamp.mapisode.navigation.GroupRoute
import kotlinx.coroutines.launch

@Composable
fun GroupDetailScreen(
	detail: GroupRoute.Detail,
	onBackClick: () -> Unit,
	onEditClick: (String) -> Unit,
	viewModel: GroupDetailViewModel = hiltViewModel(),
) {
	val context = LocalContext.current
	val uiState = viewModel.uiState.collectAsStateWithLifecycle()
	val effect = rememberFlowWithLifecycle(
		flow = viewModel.sideEffect,
		initialValue = GroupDetailSideEffect.Idle,
	).value

	LaunchedEffect(uiState.value) {
		with(uiState.value) {
			if (isGroupIdCaching) {
				viewModel.onIntent(GroupDetailIntent.InitializeGroupDetail(detail.groupId))
			}
			if (isGroupLoading) {
				viewModel.onIntent(GroupDetailIntent.TryGetGroup(detail.groupId))
			}
		}
	}

	LaunchedEffect(effect) {
		when (effect) {
			is GroupDetailSideEffect.ShowToast -> {
				Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
			}

			is GroupDetailSideEffect.NavigateToGroupEditScreen -> {
				onEditClick(effect.groupId)
			}

			is GroupDetailSideEffect.NavigateToGroupScreen -> {
				onBackClick()
			}

			is GroupDetailSideEffect.NavigateToEpisode -> {
				// TODO: Navigate to Episode
			}
		}
	}

	GroupDetailContent(
		uiState = uiState.value,
		onBackClick = {
			viewModel.onIntent(GroupDetailIntent.OnBackClick)
		},
		onEditClick = {
			viewModel.onIntent(GroupDetailIntent.OnEditClick(detail.groupId))
		},
	)
}

@Composable
fun GroupDetailContent(
	uiState: GroupDetailState,
	onBackClick: () -> Unit,
	onEditClick: () -> Unit,
) {
	val scope = rememberCoroutineScope()
	val pagerState = rememberPagerState(pageCount = { 2 })
	val tapList = listOf("그룹 설명", "에피소드")

	MapisodeScaffold(
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = uiState.group?.name ?: "",
				navigationIcon = {
					MapisodeIconButton(
						onClick = { onBackClick() },
					) {
						MapisodeIcon(
							id = R.drawable.ic_arrow_back_ios,
						)
					}
				},
				actions = {
					MapisodeIconButton(
						onClick = {
							onEditClick()
						},
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
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
				.padding(horizontal = 20.dp),
			verticalArrangement = Arrangement.Top,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			MapisodeTabRow(
				selectedTabIndex = pagerState.currentPage,
			) {
				tapList.forEachIndexed { index, _ ->
					MapisodeTab(
						text = {
							MapisodeText(tapList[index])
						},
						selected = pagerState.currentPage == index,
						onClick = {
							scope.launch {
								pagerState.animateScrollToPage(index)
							}
						},
					)
				}
			}
			HorizontalPager(state = pagerState) { page ->
				when (page) {
					0 -> GroupDetailContent(data = tapList[0])
					1 -> GroupEpisodesContent(data = tapList[1])
				}
			}
		}
	}
}

@Composable
fun GroupDetailContent(data: String) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		MapisodeText(text = data)
	}
}

@Composable
fun GroupEpisodesContent(data: String) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		MapisodeText(text = data)
	}
}
