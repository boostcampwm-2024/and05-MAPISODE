package com.boostcamp.mapisode.mygroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.tab.MapisodeTab
import com.boostcamp.mapisode.designsystem.compose.tab.MapisodeTabRow
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import kotlinx.coroutines.launch

@Composable
fun GroupDetailScreen(onBack: () -> Unit) {
	val pagerState = rememberPagerState(pageCount = { 2 })
	val list = listOf("그룹 상세", "에피소드")
	val scope = rememberCoroutineScope()

	MapisodeScaffold(
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = "그룹 상세",
				navigationIcon = {
					MapisodeIconButton(
						onClick = { onBack() },
					) {
						MapisodeIcon(
							id = R.drawable.ic_arrow_back_ios,
						)
					}
				},
				actions = {
					MapisodeIconButton(
						onClick = { },
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
			0 -> TabContentScreen(data = "그룹 상세")
			1 -> TabContentScreen(data = "에피소드")
		}
	}
}

@Composable
fun TabContentScreen(data: String) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		MapisodeText(text = data)
	}
}
