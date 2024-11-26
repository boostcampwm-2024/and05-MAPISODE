package com.boostcamp.mapisode.mygroup.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
	val pagerState = rememberPagerState(pageCount = { 2 })
	val list = listOf("그룹 상세", "에피소드")
	val scope = rememberCoroutineScope()
	val uiState = viewModel.uiState.collectAsStateWithLifecycle()

	LaunchedEffect(uiState) {
		viewModel.onIntent(GroupDetailIntent.GetGroupId(detail.groupId))
	}

	MapisodeScaffold(
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = "그룹 상세",
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
							onEditClick(detail.groupId)
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
				list.forEachIndexed { index, _ ->
					MapisodeTab(
						text = {
							MapisodeText(list[index])
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
			TabsContent(pagerState)
		}
	}
}

@Composable
fun TabsContent(pagerState: PagerState) {
	HorizontalPager(state = pagerState) { page ->
		when (page) {
			0 -> GroupDetailContent(data = "그룹 상세")
			1 -> GroupEpisodesContent(data = "에피소드")
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
