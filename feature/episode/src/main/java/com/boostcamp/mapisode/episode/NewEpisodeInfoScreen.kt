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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.categoryMap
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.groupMap
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.textFieldModifier
import com.boostcamp.mapisode.episode.intent.NewEpisodeInfo
import com.boostcamp.mapisode.episode.intent.NewEpisodeState
import com.naver.maps.geometry.LatLng
import timber.log.Timber
import java.util.Date
import java.util.Locale

@Composable
internal fun NewEpisodeInfoScreen(
	state: NewEpisodeState,
	navController: NavController,
	updateGroup: (String) -> Unit,
	updateCategory: (String) -> Unit,
	updateTags: (String) -> Unit,
	updateDate: (Date) -> Unit,
	updateEpisodeInfo: (NewEpisodeInfo) -> Unit,
) {
	var isGroupDropdownExpanded by remember { mutableStateOf(false) }
	var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
	var isGroupBlank by remember { mutableStateOf(false) }
	var isCategoryBlank by remember { mutableStateOf(false) }
	var tagValue by remember { mutableStateOf(state.episodeInfo.tags) }
	var showDatePickerDialog by remember { mutableStateOf(false) }
	val datePickerState = rememberDatePickerState()

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
									Timber.d(it.toString())
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
					colors = DatePickerDialogColors(),
				) {
					DatePicker(
						state = datePickerState,
						colors = DatePickerDialogColors(),
						showModeToggle = false,
					)
				}
			}
			Column {
				EpisodeTextFieldGroup(
					labelRes = R.string.new_episode_info_location,
					placeholderRes = R.string.new_episode_info_placeholder_location,
					value = latLngString(state.cameraPosition.target),
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
						trailingIcon = { MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_drop_down) },
						onTrailingIconClick = {
							isGroupDropdownExpanded = !isGroupDropdownExpanded
						},
						isError = isGroupBlank,
					)
					MapisodeDropdownMenu(
						expanded = isGroupDropdownExpanded,
						onDismissRequest = {
							isGroupDropdownExpanded = !isGroupDropdownExpanded
							isGroupBlank = state.episodeInfo.category.isBlank()
						},
						modifier = Modifier.fillMaxWidth(0.9f),
					) {
						for (group in groupMap.keys) {
							MapisodeDropdownMenuItem(
								onClick = {
									isGroupDropdownExpanded = !isGroupDropdownExpanded
									updateGroup(group)
								},
							) {
								MapisodeText(text = group)
							}
						}
					}
				}
				Column {
					EpisodeTextFieldGroup(
						labelRes = R.string.new_episode_info_category,
						placeholderRes = R.string.new_episode_info_placeholder_category,
						value = state.episodeInfo.category,
						readOnly = true,
						trailingIcon = { MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_drop_down) },
						onTrailingIconClick = {
							isCategoryDropdownExpanded = !isCategoryDropdownExpanded
						},
						isError = isCategoryBlank,
					)
					MapisodeDropdownMenu(
						expanded = isCategoryDropdownExpanded,
						onDismissRequest = {
							isCategoryDropdownExpanded = !isCategoryDropdownExpanded
							isCategoryBlank = state.episodeInfo.category.isBlank()
						},
						modifier = Modifier.fillMaxWidth(0.9f),
					) {
						for (category in categoryMap.keys) {
							MapisodeDropdownMenuItem(
								onClick = {
									isCategoryDropdownExpanded = !isCategoryDropdownExpanded
									updateCategory(category)
								},
							) {
								MapisodeText(text = category)
							}
						}
					}
				}
				EpisodeTextFieldGroup(
					labelRes = R.string.new_episode_info_tags,
					placeholderRes = R.string.new_episode_info_placeholder_tags,
					value = tagValue,
					onValueChange = { tagValue = it },
					onSubmitInput = { tagValue = it },
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
				modifier = textFieldModifier,
				onClick = {
					if (state.episodeInfo.group.isBlank() || state.episodeInfo.category.isBlank()) {
						isGroupBlank = state.episodeInfo.group.isBlank()
						isCategoryBlank = state.episodeInfo.category.isBlank()
						return@MapisodeFilledButton
					}
					updateEpisodeInfo(
						state.episodeInfo.copy(
							tags = tagValue,
						),
					)
					navController.navigate("new_episode_content")
				},
				text = "다음",
			)
		}
	}
}

@Composable
private fun DatePickerDialogColors(): DatePickerColors =
	DatePickerDefaults.colors().copy(
		containerColor = MapisodeTheme.colorScheme.dialogBackground,
		selectedYearContainerColor = MapisodeTheme.colorScheme.dialogConfirm,
		selectedDayContainerColor = MapisodeTheme.colorScheme.dialogConfirm,
		todayDateBorderColor = MapisodeTheme.colorScheme.dialogConfirm,
	)

private fun latLngString(latLng: LatLng): String =
	String.format(Locale.getDefault(), "%.6f, %.6f", latLng.latitude, latLng.longitude)

private fun dateString(date: Date): String =
	DateFormat.format("yyyy. MM. dd", date).toString()

@Preview
@Composable
internal fun NewEpisodeInfoScreenPreview() {
	NewEpisodeInfoScreen(
		state = NewEpisodeState(),
		navController = rememberNavController(),
		updateEpisodeInfo = {},
		updateCategory = {},
		updateGroup = {},
		updateTags = {},
		updateDate = {},
	)
}
