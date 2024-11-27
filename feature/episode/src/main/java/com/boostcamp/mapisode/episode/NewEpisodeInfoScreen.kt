package com.boostcamp.mapisode.episode

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeOutlinedButton
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenu
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenuItem
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.buttonModifier
import com.boostcamp.mapisode.episode.intent.EpisodeCategory
import com.boostcamp.mapisode.episode.intent.NewEpisodeInfo
import com.boostcamp.mapisode.episode.intent.NewEpisodeState
import java.util.Date

@Composable
internal fun NewEpisodeInfoScreen(
	state: NewEpisodeState,
	navController: NavController,
	loadMyGroups: () -> Unit,
	updateGroup: (String) -> Unit,
	updateCategory: (EpisodeCategory) -> Unit,
	updateTags: (String) -> Unit,
	updateDate: (Date) -> Unit,
	updateEpisodeInfo: (NewEpisodeInfo) -> Unit,
) {
	var isGroupDropdownExpanded by remember { mutableStateOf(false) }
	var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
	var isGroupBlank by rememberSaveable { mutableStateOf(false) }
	var isCategoryBlank by rememberSaveable { mutableStateOf(false) }
	var tagValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
		mutableStateOf(TextFieldValue(state.episodeInfo.tags))
	}
	var showDatePickerDialog by remember { mutableStateOf(false) }
	val datePickerState = rememberDatePickerState()

	LaunchedEffect(Unit) {
		loadMyGroups()
	}

	MapisodeScaffold(
		topBar = {
			NewEpisodeTopbar(navController)
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
				DatePickerDialog(
					onDismissRequest = {
						showDatePickerDialog = false
					},
					confirmButton = {
						MapisodeOutlinedButton(
							modifier = Modifier.padding(top = 8.dp),
							onClick = {
								showDatePickerDialog = false
								datePickerState.selectedDateMillis?.let {
									updateDate(Date(it))
								}
							},
							text = "확인",
						)
					},
					dismissButton = {
						MapisodeOutlinedButton(
							onClick = {
								showDatePickerDialog = false
							},
							text = "취소",
						)
					},
					colors = datePickerDialogColors(),
				) {
					DatePicker(
						state = datePickerState,
						colors = datePickerDialogColors(),
						showModeToggle = false,
					)
				}
			}
			Column {
				EpisodeTextFieldGroup(
					labelRes = R.string.new_episode_info_location,
					placeholderRes = R.string.new_episode_info_placeholder_location,
					value = state.episodeAddress,
					readOnly = true,
					trailingIcon = { MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_location) },
					onTrailingIconClick = {
						navController.navigate("pick_location")
					},
				)
				Column {
					EpisodeTextFieldGroup(
						labelRes = R.string.new_episode_info_group,
						placeholderRes = R.string.new_episode_info_placeholder_group,
						value = state.episodeInfo.group,
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
							isGroupBlank = state.episodeInfo.group.isBlank()
						},
						modifier = Modifier.fillMaxWidth(0.9f),
					) {
						for (myGroup in state.myGroups) {
							MapisodeDropdownMenuItem(
								onClick = {
									isGroupDropdownExpanded = !isGroupDropdownExpanded
									updateGroup(myGroup.name)
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
						value = state.episodeInfo.category?.categoryName ?: "",
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
							isCategoryBlank = checkCategoryIsNull(state.episodeInfo.category)
						},
						modifier = Modifier.fillMaxWidth(0.9f),
					) {
						for (category in EpisodeCategory.entries) {
							MapisodeDropdownMenuItem(
								onClick = {
									isCategoryDropdownExpanded = !isCategoryDropdownExpanded
									updateCategory(category)
								},
							) {
								MapisodeText(text = category.categoryName)
							}
						}
					}
				}
				EpisodeTextFieldGroup(
					labelRes = R.string.new_episode_info_tags,
					placeholderRes = R.string.new_episode_info_placeholder_tags,
					value = tagValue.text,
					onValueChange = { tagValue = TextFieldValue(it, TextRange(it.length)) },
					onSubmitInput = { tagValue = TextFieldValue(it, TextRange(it.length)) },
				)
				EpisodeTextFieldGroup(
					labelRes = R.string.new_episode_info_date,
					placeholderRes = R.string.new_episode_info_placeholder_date,
					value = dateString(state.episodeInfo.date),
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
					if (state.episodeInfo.group.isBlank() ||
						checkCategoryIsNull(state.episodeInfo.category)
					) {
						isGroupBlank = state.episodeInfo.group.isBlank()
						isCategoryBlank = checkCategoryIsNull(state.episodeInfo.category)
						return@MapisodeFilledButton
					}
					updateEpisodeInfo(
						state.episodeInfo.copy(
							location = state.cameraPosition.target,
							tags = tagValue.text,
						),
					)
					navController.navigate("new_episode_content")
				},
				text = stringResource(R.string.new_episode_info_next),
			)
		}
	}
}

private fun checkCategoryIsNull(category: EpisodeCategory?) = category == null

@Composable
private fun datePickerDialogColors(): DatePickerColors =
	DatePickerDefaults.colors().copy(
		containerColor = MapisodeTheme.colorScheme.dialogBackground,
		selectedYearContainerColor = MapisodeTheme.colorScheme.dialogConfirm,
		selectedDayContainerColor = MapisodeTheme.colorScheme.dialogConfirm,
		todayDateBorderColor = MapisodeTheme.colorScheme.dialogConfirm,
	)

private const val DATE_STRING_FORMAT = "yyyy. MM. dd"
private fun dateString(date: Date): String =
	DateFormat.format(DATE_STRING_FORMAT, date).toString()

@Preview
@Composable
internal fun NewEpisodeInfoScreenPreview() {
	NewEpisodeInfoScreen(
		state = NewEpisodeState(),
		navController = rememberNavController(),
		loadMyGroups = {},
		updateEpisodeInfo = {},
		updateCategory = {},
		updateGroup = {},
		updateTags = {},
		updateDate = {},
	)
}
