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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.boostcamp.mapisode.episode.intent.NewEpisodeIntent
import com.boostcamp.mapisode.episode.intent.NewEpisodeViewModel
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
	var tagValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
		mutableStateOf(TextFieldValue(uiState.episodeInfo.tags))
	}
	var showDatePickerDialog by remember { mutableStateOf(false) }
	val datePickerState = rememberDatePickerState()

	LaunchedEffect(Unit) {
		viewModel.onIntent(NewEpisodeIntent.LoadMyGroups)
	}

	MapisodeScaffold(
		topBar = {
			NewEpisodeTopBar(
				onClickBack = {
					onPopBack()
				},
				onClickClear = {
					viewModel.onIntent(NewEpisodeIntent.ClearEpisode)
					onPopBackToMain()
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
									viewModel.onIntent(NewEpisodeIntent.SetEpisodeDate(Date(it)))
								}
							},
							text = stringResource(R.string.new_episode_menu_ok),
						)
					},
					dismissButton = {
						MapisodeOutlinedButton(
							onClick = {
								showDatePickerDialog = false
							},
							text = stringResource(R.string.new_episode_menu_cancel),
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
								tags = tagValue.text,
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
		onPopBack = {},
		onPopBackToMain = {},
		onClickPickLocation = {},
		onNavigateToContent = {},
	)
}
