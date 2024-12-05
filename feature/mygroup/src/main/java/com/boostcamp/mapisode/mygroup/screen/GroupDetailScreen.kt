package com.boostcamp.mapisode.mygroup.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.common.util.toFormattedString
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.Direction
import com.boostcamp.mapisode.designsystem.compose.MapisodeCircularLoadingIndicator
import com.boostcamp.mapisode.designsystem.compose.MapisodeDialog
import com.boostcamp.mapisode.designsystem.compose.MapisodeDivider
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.Thickness
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeOutlinedButton
import com.boostcamp.mapisode.designsystem.compose.card.GroupInfoCard
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenu
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenuItem
import com.boostcamp.mapisode.designsystem.compose.tab.MapisodeTab
import com.boostcamp.mapisode.designsystem.compose.tab.MapisodeTabRow
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.model.GroupModel
import com.boostcamp.mapisode.mygroup.intent.GroupDetailIntent
import com.boostcamp.mapisode.mygroup.model.GroupUiEpisodeModel
import com.boostcamp.mapisode.mygroup.model.GroupUiMemberModel
import com.boostcamp.mapisode.mygroup.sideeffect.GroupDetailSideEffect
import com.boostcamp.mapisode.mygroup.sideeffect.rememberFlowWithLifecycle
import com.boostcamp.mapisode.mygroup.state.GroupDetailState
import com.boostcamp.mapisode.mygroup.viewmodel.GroupDetailViewModel
import com.boostcamp.mapisode.navigation.GroupRoute
import kotlinx.coroutines.launch
import com.boostcamp.mapisode.mygroup.R as S

@Composable
fun GroupDetailScreen(
	detail: GroupRoute.Detail,
	onBackClick: () -> Unit,
	onEditClick: (String) -> Unit,
	onEpisodeClick: (String) -> Unit,
	viewModel: GroupDetailViewModel = hiltViewModel(),
) {
	val context = LocalContext.current
	val uiState = viewModel.uiState.collectAsStateWithLifecycle()
	val effect = rememberFlowWithLifecycle(
		flow = viewModel.sideEffect,
		initialValue = GroupDetailSideEffect.Idle,
	).value

	var showDialog by remember { mutableStateOf(false) }

	if (showDialog) {
		MapisodeDialog(
			onResultRequest = { isPositive ->
				if (isPositive) {
					viewModel.onIntent(GroupDetailIntent.OnGroupOutConfirm)
				} else {
					viewModel.onIntent(GroupDetailIntent.OnGroupOutCancel)
				}
			},
			onDismissRequest = {
				viewModel.onIntent(GroupDetailIntent.OnGroupOutCancel)
			},
			titleText = stringResource(S.string.dialog_group_out_title),
			contentText = stringResource(S.string.dialog_group_out_message),
			confirmText = stringResource(S.string.dialog_group_out_positive),
			cancelText = stringResource(S.string.dialog_group_out_negative),
		)
	}

	LaunchedEffect(uiState.value) {
		with(uiState.value) {
			if (isGroupIdCaching) {
				viewModel.onIntent(GroupDetailIntent.InitializeGroupDetail(detail.groupId))
			}
			if (isGroupLoading) {
				viewModel.onIntent(GroupDetailIntent.TryGetGroup(detail.groupId))
			}
			if (!isGroupIdCaching && !isGroupLoading && membersInfo.isEmpty()) {
				viewModel.onIntent(GroupDetailIntent.TryGetUserInfo)
			}
			// 최초 진입 시 보이지 않는 탭, 후순위 로딩
			if (episodes.isEmpty() && membersInfo.isNotEmpty()) {
				viewModel.onIntent(GroupDetailIntent.TryGetGroupEpisodes)
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
				onEpisodeClick(effect.episodeId)
			}

			is GroupDetailSideEffect.IssueInvitationCode -> {
				val clipboard =
					context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
				val clip = ClipData.newPlainText("label", effect.invitationCode)
				clipboard.setPrimaryClip(clip)
			}

			is GroupDetailSideEffect.WarnGroupOut -> {
				showDialog = true
			}

			is GroupDetailSideEffect.RemoveDialog -> {
				showDialog = false
			}
		}
	}

	GroupDetailContent(
		uiState = uiState.value,
		onBackClick = {
			viewModel.onIntent(GroupDetailIntent.OnBackClick)
		},
		onEditClick = {
			viewModel.onIntent(GroupDetailIntent.OnEditClick)
		},
		onIssueCodeClick = {
			viewModel.onIntent(GroupDetailIntent.OnIssueCodeClick)
		},
		onGroupOutClick = {
			viewModel.onIntent(GroupDetailIntent.OnGroupOutClick)
		},
		onEpisodeClick = { episodeId ->
			viewModel.onIntent(GroupDetailIntent.OnEpisodeClick(episodeId))
		},
	)
}

@Composable
fun GroupDetailContent(
	uiState: GroupDetailState,
	onBackClick: () -> Unit,
	onEditClick: () -> Unit,
	onIssueCodeClick: () -> Unit,
	onGroupOutClick: () -> Unit,
	onEpisodeClick: (String) -> Unit,
) {
	val scope = rememberCoroutineScope()
	val pagerState = rememberPagerState(pageCount = { 2 })
	val tapList = listOf("그룹 설명", "에피소드")

	MapisodeScaffold(
		isStatusBarPaddingExist = true,
		isNavigationBarPaddingExist = true,
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
					if (uiState.isGroupOwner) {
						MapisodeIconButton(
							onClick = {
								onEditClick()
							},
						) {
							MapisodeIcon(
								id = R.drawable.ic_edit,
							)
						}
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
								members = uiState.membersInfo,
								onIssueCodeClick = onIssueCodeClick,
								onGroupOutClick = onGroupOutClick,
							)
						}
					}

					1 -> {
						GroupEpisodesContent(
							episodes = uiState.episodes,
							onEpisodeClick = onEpisodeClick,
						)
					}
				}
			}
		}
	}
}

@Composable
fun GroupDetailContent(
	group: GroupModel,
	members: List<GroupUiMemberModel>,
	onIssueCodeClick: () -> Unit,
	onGroupOutClick: () -> Unit,
) {
	val outerScrollState = rememberScrollState()
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(horizontal = 20.dp, vertical = 10.dp)
			.verticalScroll(outerScrollState),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		GroupInfoCard(
			group = group,
		)

		Spacer(modifier = Modifier.padding(5.dp))

		MapisodeDivider(direction = Direction.Horizontal, thickness = Thickness.Thin)

		Spacer(modifier = Modifier.padding(5.dp))

		MapisodeText(
			modifier = Modifier
				.fillMaxWidth()
				.padding(start = 4.dp),
			text = stringResource(S.string.group_description_label),
			style = MapisodeTheme.typography.labelLarge,
		)

		Spacer(modifier = Modifier.padding(2.dp))

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
				.fillMaxWidth()
				.heightIn(min = 52.dp, max = 80.dp),
			onClick = { onIssueCodeClick() },
			text = stringResource(S.string.btn_issue_code),
			showRipple = true,
		)

		Spacer(modifier = Modifier.padding(10.dp))

		MapisodeText(
			modifier = Modifier
				.fillMaxWidth()
				.padding(start = 4.dp),
			text = stringResource(S.string.label_detail_group_member),
			style = MapisodeTheme.typography.labelLarge,
		)

		Spacer(modifier = Modifier.padding(2.dp))

		Column(
			modifier = Modifier
				.fillMaxWidth(),
			verticalArrangement = Arrangement.Top,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			if (members.isEmpty()) {
				Box(
					modifier = Modifier.aspectRatio(1.2f),
					contentAlignment = Alignment.Center,
				) {
					MapisodeCircularLoadingIndicator()
				}
			} else {
				members.forEach {
					GroupMemberContent(it)
					Spacer(modifier = Modifier.padding(5.dp))
				}
				MapisodeOutlinedButton(
					modifier = Modifier
						.fillMaxWidth()
						.heightIn(min = 40.dp, max = 80.dp),
					borderColor = MapisodeTheme.colorScheme.textColoredContainer,
					onClick = { onGroupOutClick() },
					text = stringResource(S.string.btn_group_out),
					showRipple = true,
				)
			}
		}
	}
}

@Composable
fun GroupEpisodesContent(
	episodes: List<GroupUiEpisodeModel>,
	onEpisodeClick: (String) -> Unit,
) {
	var expanded by remember { mutableStateOf(false) }
	var selectedSortOption by remember { mutableIntStateOf(0) }
	val menuItemList = listOf("최신순", "과거순", "이름순")
	var sortedEpisodes by remember { mutableStateOf(episodes) }

	when (selectedSortOption) {
		0 -> sortedEpisodes = episodes.sortedByDescending { it.createdAt }
		1 -> sortedEpisodes = episodes.sortedBy { it.createdAt }
		2 -> sortedEpisodes = episodes.sortedBy { it.title }
	}

	LazyColumn(
		modifier = Modifier.fillMaxSize(),
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
					text = stringResource(S.string.label_episode),
					style = MapisodeTheme.typography.labelLarge,
				)
				MapisodeIconButton(
					onClick = { expanded = true },
				) {
					Row(
						verticalAlignment = Alignment.CenterVertically,
					) {
						MapisodeText(
							text = menuItemList[selectedSortOption],
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
						menuItemList.forEachIndexed { index, item ->
							MapisodeDropdownMenuItem(
								onClick = {
									selectedSortOption = index
									expanded = false
								},
							) {
								MapisodeText(
									text = item,
								)
							}
						}
					}
				}
			}
		}
		items(sortedEpisodes) { episode ->
			EpisodeCard(episode, onEpisodeClick = onEpisodeClick)
		}
	}
}

@Composable
fun GroupMemberContent(
	member: GroupUiMemberModel,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(72.dp)
			.border(
				width = 1.dp,
				color = MapisodeTheme.colorScheme.textColoredContainer,
				shape = RoundedCornerShape(8.dp),
			)
			.padding(10.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		AsyncImage(
			model = member.profileUrl,
			contentDescription = "",
			modifier = Modifier
				.size(40.dp)
				.clip(CircleShape),
			contentScale = ContentScale.Crop,
		)

		Spacer(modifier = Modifier.width(10.dp))

		Column(
			modifier = Modifier.weight(1f),
			verticalArrangement = Arrangement.Center,
		) {
			MapisodeText(
				text = member.name,
				style = MapisodeTheme.typography.labelLarge,
				maxLines = 1,
			)
			Spacer(modifier = Modifier.padding(2.dp))
			MapisodeText(
				text = stringResource(S.string.content_member_joined_at) +
					member.joinedAt.toFormattedString(),
				style = MapisodeTheme.typography.labelMedium,
				maxLines = 1,
			)
			MapisodeText(
				text = buildString {
					append(stringResource(S.string.content_recent_episode_upload))
					append(member.recentCreatedAt?.toFormattedString() ?: "없음")
				},
				style = MapisodeTheme.typography.labelMedium,
				maxLines = 1,
			)
		}

		Column(
			horizontalAlignment = Alignment.End,
		) {
			MapisodeText(
				text = stringResource(S.string.content_episode_count),
				style = MapisodeTheme.typography.labelMedium,
				maxLines = 1,
			)
			MapisodeText(
				text = member.countEpisode.toString() +
					stringResource(S.string.content_number_count),
				style = MapisodeTheme.typography.labelMedium,
				maxLines = 1,
			)
		}
	}
}

@Composable
fun EpisodeCard(
	episode: GroupUiEpisodeModel,
	onEpisodeClick: (String) -> Unit,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(130.dp)
			.background(
				color = MapisodeTheme.colorScheme.episodeBoxBackground,
				shape = RoundedCornerShape(8.dp),
			)
			.border(
				width = 1.dp,
				color = MapisodeTheme.colorScheme.episodeBoxStroke,
				shape = RoundedCornerShape(8.dp),
			)
			.padding(10.dp)
			.clickable {
				onEpisodeClick(episode.id)
			},
	) {
		// Image on the left
		AsyncImage(
			model = episode.imageUrls.first(),
			contentDescription = "Episode Image",
			modifier = Modifier
				.size(110.dp)
				.clip(RoundedCornerShape(8.dp)),
			contentScale = ContentScale.Crop,
		)

		Spacer(modifier = Modifier.width(10.dp))

		Column(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
		) {
			MapisodeText(
				text = episode.title,
				style = MapisodeTheme.typography.titleMedium
					.copy(fontWeight = FontWeight.SemiBold),
				maxLines = 1,
			)
			Spacer(modifier = Modifier.padding(4.dp))

			val textList = listOf(
				stringResource(S.string.overview_created_by) + episode.createdBy,
				stringResource(S.string.overview_location) + episode.address,
				stringResource(S.string.overview_date) + episode.createdAt.toFormattedString(),
				stringResource(S.string.overview_content) + episode.content,
			)

			textList.forEach { text ->
				MapisodeText(
					text = text,
					style = MapisodeTheme.typography.labelMedium,
					maxLines = 1,
				)
			}
		}
	}
}
