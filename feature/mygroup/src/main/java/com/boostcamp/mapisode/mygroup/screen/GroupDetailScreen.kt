package com.boostcamp.mapisode.mygroup.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeDivider
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.Thickness
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.card.GroupInfoCard
import com.boostcamp.mapisode.designsystem.compose.tab.MapisodeTab
import com.boostcamp.mapisode.designsystem.compose.tab.MapisodeTabRow
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.model.GroupModel
import com.boostcamp.mapisode.mygroup.R as S
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
				Toast.makeText(context, effect.messageResId, Toast.LENGTH_SHORT).show()
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

			is GroupDetailSideEffect.IssueInvitationCode -> {
				val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
				val clip = ClipData.newPlainText("label", effect.invitationCode)
				clipboard.setPrimaryClip(clip)
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
		onIssueCodeClick = {
			viewModel.onIntent(GroupDetailIntent.OnIssueCodeClick)
		},
	)
}

@Composable
fun GroupDetailContent(
	uiState: GroupDetailState,
	onBackClick: () -> Unit,
	onEditClick: () -> Unit,
	onIssueCodeClick: () -> Unit,
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
				.padding(it),
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
					0 -> {
						if (uiState.group != null) {
							GroupDetailContent(
								group = uiState.group,
								onIssueCodeClick = onIssueCodeClick,
							)
						}
					}

					1 -> {
					}
				}
			}
		}
	}
}

@Composable
fun GroupDetailContent(
	group: GroupModel,
	onIssueCodeClick: () -> Unit,
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(horizontal = 20.dp, vertical = 10.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		GroupInfoCard(
			group = group,
		)

		Spacer(modifier = Modifier.padding(5.dp))

		MapisodeDivider(thickness = Thickness.Thin)

		Spacer(modifier = Modifier.padding(5.dp))

		MapisodeText(
			modifier = Modifier
				.fillMaxWidth()
				.padding(start = 4.dp),
			text = stringResource(S.string.group_description_label),
			style = MapisodeTheme.typography.labelLarge,
		)

		Spacer(modifier = Modifier.padding(5.dp))

		Box(
			modifier = Modifier
				.clip(RoundedCornerShape(8.dp))
				.background(MapisodeTheme.colorScheme.textColoredContainer)
				.padding(10.dp),
		) {
			MapisodeText(
				modifier = Modifier
					.fillMaxWidth()
					.wrapContentHeight()
					.heightIn(min = 50.dp)
					.padding(start = 4.dp),
				text = group.description,
				style = MapisodeTheme.typography.labelMedium,
			)
		}

		Spacer(modifier = Modifier.padding(10.dp))

		MapisodeFilledButton(
			modifier = Modifier
				.fillMaxWidth(),
			onClick = { onIssueCodeClick() },
			text = stringResource(S.string.group_btn_issue_code),
			showRipple = true,
		)
	}
}

@Composable
fun GroupEpisodesContent(
	episodes: EpisodeModel,
) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
	}
}
