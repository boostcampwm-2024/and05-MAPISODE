package com.boostcamp.mapisode.episode

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.datepicker.MapisodeDatePicker
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenu
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenuItem
import com.boostcamp.mapisode.designsystem.theme.MapisodeTextStyle
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.buttonModifier
import com.boostcamp.mapisode.episode.intent.EpisodeCategory
import com.boostcamp.mapisode.episode.intent.NewEpisodeIntent
import com.boostcamp.mapisode.episode.intent.NewEpisodeViewModel
import java.time.ZoneId
import java.util.Date

@Composable
internal fun NewEpisodeInfoScreen(
	viewModel: NewEpisodeViewModel = hiltViewModel(),
	onPopBack: () -> Unit,
	onPopBackToMain: () -> Unit,
	onClickPickLocation: () -> Unit,
	onNavigateToContent: () -> Unit,
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	var isGroupDropdownExpanded by remember { mutableStateOf(false) }
	var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
	var isGroupBlank by rememberSaveable { mutableStateOf(false) }
	var isCategoryBlank by rememberSaveable { mutableStateOf(false) }
	var tagValue by rememberSaveable { mutableStateOf(uiState.episodeInfo.tags) }
	var showDatePickerDialog by remember { mutableStateOf(false) }
	var showClearDialog by rememberSaveable { mutableStateOf(false) }

	LaunchedEffect(Unit) {
		viewModel.onIntent(NewEpisodeIntent.LoadMyGroups)
	}

	if (showClearDialog) {
		ClearDialog(
			onResultRequest = { result ->
				if (result) {
					viewModel.onIntent(NewEpisodeIntent.ClearEpisode)
					onPopBackToMain()
				}
			},
			onDismissRequest = {
				showClearDialog = false
			},
		)
	}

	MapisodeScaffold(
		topBar = {
			NewEpisodeTopBar(
				onClickBack = {
					onPopBack()
				},
				onClickClear = {
					showClearDialog = true
				},
			)
		},
		isStatusBarPaddingExist = true,
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
				.padding(20.dp),
			verticalArrangement = Arrangement.SpaceBetween,
		) {
			if (showDatePickerDialog) {
				MapisodeDatePicker(
					onDismissRequest = { showDatePickerDialog = false },
					onDateSelected = { localDate ->
						viewModel.onIntent(
							NewEpisodeIntent.SetEpisodeDate(
								Date.from(
									localDate.atStartOfDay(
										ZoneId.systemDefault(),
									).toInstant(),
								),
							),
						)
						showDatePickerDialog = false
					},
					initialDate = uiState.episodeInfo.date
						.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate(),
				)
			}
			Column {
				EpisodeTextFieldGroup(
					labelRes = R.string.new_episode_info_location,
					placeholderRes = R.string.new_episode_info_placeholder_location,
					value = uiState.episodeAddress,
					readOnly = true,
					trailingIcon = { MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_location) },
					onTrailingIconClick = {
						onClickPickLocation()
					},
				)
				Column {
					EpisodeTextFieldGroup(
						labelRes = R.string.new_episode_info_group,
						placeholderRes = R.string.new_episode_info_placeholder_group,
						value = uiState.episodeInfo.group,
						readOnly = true,
						trailingIcon = {
							MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_drop_down)
						},
						onTrailingIconClick = {
							isGroupDropdownExpanded = !isGroupDropdownExpanded
						},
						isError = isGroupBlank,
					)
					MapisodeDropdownMenu(
						expanded = isGroupDropdownExpanded,
						onDismissRequest = {
							isGroupDropdownExpanded = !isGroupDropdownExpanded
							isGroupBlank = uiState.episodeInfo.group.isBlank()
						},
						modifier = Modifier.fillMaxWidth(0.9f),
					) {
						for (myGroup in uiState.myGroups) {
							MapisodeDropdownMenuItem(
								onClick = {
									isGroupDropdownExpanded = !isGroupDropdownExpanded
									viewModel.onIntent(NewEpisodeIntent.SetEpisodeGroup(myGroup.name))
								},
							) {
								MapisodeText(text = myGroup.name)
							}
						}
					}
				}
				Column {
					EpisodeTextFieldGroup(
						labelRes = R.string.new_episode_info_category,
						placeholderRes = R.string.new_episode_info_placeholder_category,
						value = uiState.episodeInfo.category?.categoryName ?: "",
						readOnly = true,
						trailingIcon = {
							MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_drop_down)
						},
						onTrailingIconClick = {
							isCategoryDropdownExpanded = !isCategoryDropdownExpanded
						},
						isError = isCategoryBlank,
					)
					MapisodeDropdownMenu(
						expanded = isCategoryDropdownExpanded,
						onDismissRequest = {
							isCategoryDropdownExpanded = !isCategoryDropdownExpanded
							isCategoryBlank = checkCategoryIsNull(uiState.episodeInfo.category)
						},
						modifier = Modifier.fillMaxWidth(0.9f),
					) {
						for (category in EpisodeCategory.entries) {
							MapisodeDropdownMenuItem(
								onClick = {
									isCategoryDropdownExpanded = !isCategoryDropdownExpanded
									viewModel.onIntent(NewEpisodeIntent.SetEpisodeCategory(category))
								},
							) {
								MapisodeText(text = category.categoryName)
							}
						}
					}
				}

				MapisodeText(
					modifier = Modifier
						.fillMaxWidth()
						.padding(start = 4.dp),
					text = "태그",
					style = MapisodeTheme.typography.labelLarge,
				)

				Spacer(modifier = Modifier.height(8.dp))

				MapisodeTagInputField(
					tagList = uiState.episodeInfo.tags,
					onTagChange = { newTags -> tagValue = newTags },
				)

				EpisodeTextFieldGroup(
					labelRes = R.string.new_episode_info_date,
					placeholderRes = R.string.new_episode_info_placeholder_date,
					value = dateString(uiState.episodeInfo.date),
					readOnly = true,
					trailingIcon = {
						MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_calendar)
					},
					onTrailingIconClick = {
						showDatePickerDialog = true
					},
				)
			}
			MapisodeFilledButton(
				modifier = buttonModifier,
				onClick = {
					if (uiState.episodeInfo.group.isBlank() ||
						checkCategoryIsNull(uiState.episodeInfo.category)
					) {
						isGroupBlank = uiState.episodeInfo.group.isBlank()
						isCategoryBlank = checkCategoryIsNull(uiState.episodeInfo.category)
						return@MapisodeFilledButton
					}
					viewModel.onIntent(
						NewEpisodeIntent.SetEpisodeInfo(
							uiState.episodeInfo.copy(
								location = uiState.cameraPosition.target,
								tags = tagValue,
							),
						),
					)
					onNavigateToContent()
				},
				text = stringResource(R.string.new_episode_info_next),
			)
		}
	}
}

private fun checkCategoryIsNull(category: EpisodeCategory?) = category == null

private const val DATE_STRING_FORMAT = "yyyy. MM. dd"
private fun dateString(date: Date): String =
	DateFormat.format(DATE_STRING_FORMAT, date).toString()

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MapisodeTagInputField(
	tagList: List<String>,
	onTagChange: (List<String>) -> Unit,
) {
	var text by rememberSaveable { mutableStateOf("") }
	var updatedTagList by remember { mutableStateOf(tagList) }

	FlowRow(
		modifier = Modifier
			.fillMaxWidth()
			.background(MapisodeTheme.colorScheme.tagFieldBackground, RoundedCornerShape(8.dp))
			.border(1.dp, MapisodeTheme.colorScheme.tagBorder, RoundedCornerShape(8.dp))
			.padding(8.dp),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalArrangement = Arrangement.Center,
	) {
		updatedTagList.forEach { tag ->
			Row(
				modifier = Modifier
					.padding(vertical = 4.dp)
					.background(
						MapisodeTheme.colorScheme.tagBackground,
						RoundedCornerShape(8.dp),
					)
					.padding(horizontal = 8.dp, vertical = 4.dp),
			) {
				MapisodeText(text = tag, color = MapisodeTheme.colorScheme.tagText)
				Spacer(modifier = Modifier.width(4.dp))
				MapisodeIcon(
					id = com.boostcamp.mapisode.designsystem.R.drawable.ic_clear,
					modifier = Modifier
						.align(Alignment.CenterVertically)
						.clickable {
							updatedTagList -= tag
							onTagChange(updatedTagList)
						},
					iconSize = IconSize.EExtraSmall,
				)
			}
		}

		BasicTextField(
			value = text,
			onValueChange = { newText ->
				if (newText.endsWith(" ")) {
					updatedTagList += (newText.trim())
					onTagChange(updatedTagList)
					text = ""
				} else {
					text = newText
				}
			},
			modifier = Modifier
				.weight(1f)
				.fillMaxRowHeight()
				.padding(4.dp),
			textStyle = MapisodeTextStyle.Default.toTextStyle().copy(
				lineHeightStyle = LineHeightStyle(
					LineHeightStyle.Alignment.Bottom,
					LineHeightStyle.Default.trim,
				),
			),
		)
	}
}

@Preview
@Composable
internal fun NewEpisodeInfoScreenPreview() {
	NewEpisodeInfoScreen(
		onPopBack = {},
		onPopBackToMain = {},
		onClickPickLocation = {},
		onNavigateToContent = {},
	)
}
