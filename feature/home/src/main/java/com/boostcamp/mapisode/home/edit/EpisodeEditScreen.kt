package com.boostcamp.mapisode.home.edit

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.common.util.toFormattedString
import com.boostcamp.mapisode.common.util.toLatLng
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeCircularLoadingIndicator
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.MapisodeTextField
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeImageButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeOutlinedButton
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenu
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenuItem
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import java.util.Date

@Composable
fun EpisodeEditRoute(
	episodeId: String = "03eee421c48e48f991bd9cfd6398d632\n",
	viewModel: EpisodeEditViewModel = hiltViewModel(),
	onBackClick: () -> Unit = {},
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val context = LocalContext.current

	LaunchedEffect(Unit) {
		viewModel.onIntent(EpisodeEditIntent.LoadEpisode(episodeId))
	}

	LaunchedEffect(Unit) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is EpisodeEditSideEffect.ShowToast -> {
					val message = context.getString(sideEffect.messageResId)
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
				}

				EpisodeEditSideEffect.NavigateToEpisodeDetailScreen -> {
					onBackClick()
				}

				else -> {}
			}
		}
	}

	if (uiState.isInitializing) {
		Box(
			modifier = Modifier.fillMaxSize(),
			contentAlignment = Alignment.Center,
		) {
			MapisodeCircularLoadingIndicator()
		}
	} else if (uiState.isSelectingPicture) {
		PictureSelectionScreen(
			onPickPhotos = { viewModel.onIntent(EpisodeEditIntent.OnSetPictures(it)) },
		)
	} else if (uiState.isSelectingLocation) {
		LocationSelectionScreen(
			episodeAddress = uiState.editedEpisode?.address ?: "",
			cameraPosition = CameraPosition(
				uiState.editedEpisode?.location?.toLatLng()
					?: LatLng(37.0, 37.0),
				16.0,
			),
			onSetEpisodeLocation = { viewModel.onIntent(EpisodeEditIntent.OnSetLocation(it)) },
			onPopBackToInfo = { viewModel.onIntent(EpisodeEditIntent.OnBackClick) },
		)
	} else {
		EpisodeEditScreen(
			state = uiState,
			onPickPhotos = { viewModel.onIntent(EpisodeEditIntent.OnPictureClick) },
			onLocationClick = { viewModel.onIntent(EpisodeEditIntent.OnLocationClick(it)) },
			onEditClick = { viewModel.onIntent(EpisodeEditIntent.OnEditClick(it)) },
			onBackClick = onBackClick,
		)
	}
}

@Composable
fun EpisodeEditScreen(
	state: EpisodeEditState,
	modifier: Modifier = Modifier,
	onPickPhotos: () -> Unit,
	onLocationClick: (EpisodeLatLng) -> Unit,
	onEditClick: (EpisodeEditState) -> Unit,
	onBackClick: () -> Unit = {},
) {
	var title by rememberSaveable { mutableStateOf(state.episode?.title ?: "") }
	var description by rememberSaveable { mutableStateOf(state.episode?.content ?: "") }
	var group by rememberSaveable { mutableStateOf(state.episode?.group ?: "") }
	var category by rememberSaveable { mutableStateOf(state.episode?.category ?: "") }
	var tag by rememberSaveable { mutableStateOf(state.episode?.tags ?: listOf()) }
	var date by rememberSaveable {
		mutableStateOf(state.episode?.memoryDate ?: Date())
	}
	var isMenuPoppedUp by remember { mutableStateOf(false) }
	var showDatePickerDialog by remember { mutableStateOf(false) }
	val datePickerState = rememberDatePickerState()

	MapisodeScaffold(
		isNavigationBarPaddingExist = true,
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = "에피소드 편집",
				navigationIcon = {
					MapisodeIconButton(
						onClick = onBackClick,
					) {
						MapisodeIcon(
							id = R.drawable.ic_arrow_back_ios,
							iconSize = IconSize.ExtraSmall,
						)
					}
				},
			)
		},
	) {
		Column(
			modifier = modifier
				.fillMaxSize()
				.padding(it)
				.padding(horizontal = 20.dp)
				.verticalScroll(rememberScrollState()),
		) {
			Box(
				modifier = Modifier.fillMaxWidth(),
				contentAlignment = Alignment.Center,
			) {
				Row(
					modifier = Modifier
						.wrapContentWidth()
						.horizontalScroll(rememberScrollState()),
					horizontalArrangement = Arrangement.spacedBy(4.dp),
					verticalAlignment = Alignment.CenterVertically,
				) {
					state.episode?.imageUrls?.forEach { imageUrl ->
						AsyncImage(
							model = imageUrl,
							contentDescription = "애피소드 이미지",
							modifier = Modifier
								.size(110.dp)
								.clip(RoundedCornerShape(16.dp)),
						)
					}
					MapisodeImageButton(
						onClick = onPickPhotos,
						showImage = true,
						modifier = Modifier
							.size(110.dp)
							.clip(RoundedCornerShape(16.dp)),
						imageContent = {},
					)
				}
			}

			Spacer(modifier = Modifier.height(20.dp))

			MapisodeText(
				modifier = Modifier
					.fillMaxWidth()
					.padding(start = 4.dp),
				text = "장소",
				style = MapisodeTheme.typography.labelLarge,
			)

			Spacer(modifier = Modifier.height(8.dp))

			MapisodeButton(
				modifier = modifier
					.fillMaxWidth()
					.height(42.dp),
				onClick = {
					onLocationClick(
						state.episode?.location ?: EpisodeLatLng(),
					)
				},
				showBorder = true,
				backgroundColors = MapisodeTheme.colorScheme.outlineButtonBackground,
				contentColor = MapisodeTheme.colorScheme.outlineButtonContent,
			) {
				MapisodeText(
					modifier = Modifier.fillMaxWidth(),
					text = state.episode?.address ?: "장소를 입력해주세요",
				)
				Spacer(modifier = Modifier.width(8.dp))
				MapisodeIcon(
					id = R.drawable.ic_location,
				)
			}

			Spacer(modifier = Modifier.height(20.dp))

			MapisodeText(
				modifier = Modifier
					.fillMaxWidth()
					.padding(start = 4.dp),
				text = "제목",
				style = MapisodeTheme.typography.labelLarge,
			)

			Spacer(modifier = Modifier.height(8.dp))

			MapisodeTextField(
				modifier = modifier.fillMaxWidth(),
				value = title,
				onValueChange = { titleText -> title = titleText },
				placeholder = "제목을 입력해주세요",
				isError = title.isEmpty(),
			)

			Spacer(modifier = Modifier.height(20.dp))

			MapisodeText(
				modifier = Modifier
					.fillMaxWidth()
					.padding(start = 4.dp),
				text = "내용",
				style = MapisodeTheme.typography.labelLarge,
			)

			Spacer(modifier = Modifier.height(8.dp))

			MapisodeTextField(
				modifier = modifier.fillMaxWidth(),
				value = description,
				onValueChange = { descriptionText -> description = descriptionText },
				placeholder = "내용을 입력해주세요",
				isError = description.isEmpty(),
			)

			Spacer(modifier = Modifier.height(20.dp))

			MapisodeText(
				modifier = Modifier
					.fillMaxWidth()
					.padding(start = 4.dp),
				text = "그룹",
				style = MapisodeTheme.typography.labelLarge,
			)

			Spacer(modifier = Modifier.height(8.dp))

			MapisodeTextField(
				modifier = modifier
					.fillMaxWidth()
					.clickable {},
				value = group,
				onValueChange = { groupText -> group = groupText },
				readOnly = true,
				placeholder = "그룹을 입력해주세요",
				isError = group.isEmpty(),
			)

			MapisodeDropdownMenu(
				expanded = isMenuPoppedUp,
				onDismissRequest = { isMenuPoppedUp = false },
				offset = DpOffset(0.dp, 0.dp).minus(DpOffset(0.dp, 0.dp)),
				modifier = Modifier.fillMaxWidth(),
			) {
				state.groups.forEach {
					MapisodeDropdownMenuItem(
						onClick = { group = it.name },
					) {
						MapisodeText(
							text = it.name,
						)
					}
				}
			}

			Spacer(modifier = Modifier.height(20.dp))

			MapisodeText(
				modifier = Modifier
					.fillMaxWidth()
					.padding(start = 4.dp),
				text = "카테고리",
				style = MapisodeTheme.typography.labelLarge,
			)

			Spacer(modifier = Modifier.height(8.dp))

			MapisodeTextField(
				modifier = modifier
					.fillMaxWidth()
					.clickable { isMenuPoppedUp = true },
				value = category,
				onValueChange = { categoryText -> category = categoryText },
				readOnly = true,
				placeholder = "카테고리를 입력해주세요",
				isError = category.isEmpty(),
			)

			MapisodeDropdownMenu(
				expanded = isMenuPoppedUp,
				onDismissRequest = { isMenuPoppedUp = false },
				offset = DpOffset(0.dp, 0.dp).minus(DpOffset(0.dp, 0.dp)),
				modifier = Modifier.fillMaxWidth(),
			) {
				persistentListOf("볼거리", "먹거리", "나머지").forEach {
					MapisodeDropdownMenuItem(
						onClick = { category = it },
					) {
						MapisodeText(
							text = it,
						)
					}
				}
			}

			Spacer(modifier = Modifier.height(20.dp))

			MapisodeText(
				modifier = Modifier
					.fillMaxWidth()
					.padding(start = 4.dp),
				text = "태그",
				style = MapisodeTheme.typography.labelLarge,
			)

			TagInputField(
				tags = state.episode?.tags?.joinToString { " " } ?: "",
				onTagChange = { newTags -> tag = newTags },
			)

			Spacer(modifier = Modifier.height(20.dp))

			MapisodeText(
				modifier = Modifier
					.fillMaxWidth()
					.padding(start = 4.dp),
				text = "날짜",
				style = MapisodeTheme.typography.labelLarge,
			)

			Spacer(modifier = Modifier.height(8.dp))

			MapisodeImageButton(
				modifier = modifier
					.fillMaxWidth()
					.height(42.dp),
				onClick = { showDatePickerDialog = true },
				showImage = false,
				imageContent = {
					MapisodeText(
						text = date.toFormattedString(),
					)
				},
			)

			if (showDatePickerDialog) {
				DatePickerDialog(
					onDismissRequest = { showDatePickerDialog = false },
					confirmButton = {
						MapisodeOutlinedButton(
							modifier = Modifier.padding(top = 8.dp),
							onClick = {
								showDatePickerDialog = false
								datePickerState.selectedDateMillis?.let { milli ->
									date = Date(milli)
								}
							},
							text = "확인",
						)
					},
					dismissButton = {
						MapisodeOutlinedButton(
							onClick = { showDatePickerDialog = false },
							text = "취소",
						)
					},
				) {
					DatePicker(
						state = datePickerState,
						showModeToggle = false,
					)
				}
			}

			Spacer(modifier = Modifier.height(20.dp))

			MapisodeFilledButton(
				modifier = modifier
					.fillMaxWidth()
					.height(52.dp),
				onClick = {
					onEditClick(
						EpisodeEditState(
							episode = state.episode?.copy(
								title = title,
								content = description,
								group = group,
								category = category,
								tags = tag,
								memoryDate = date,
							),
						),
					)
				},
				text = "수정하기",
			)
		}
	}
}

@Composable
fun PictureSelectionScreen(
	onPickPhotos: (PersistentList<Uri>) -> Unit,
) {
	val photoPickLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.PickMultipleVisualMedia(4),
		onResult = { uris ->
			onPickPhotos(uris.toPersistentList())
		},
	)
	LaunchedEffect(Unit) {
		photoPickLauncher.launch(
			PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
		)
	}
}


@Composable
fun TagInputField(
	tags: String,
	onTagChange: (List<String>) -> Unit,
) {
	var text by rememberSaveable { mutableStateOf("") }
	val tagList = tags.split(" ").filter { it.isNotEmpty() }.toMutableList()

	Column(
		modifier = Modifier.wrapContentHeight(),
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(4.dp)
				.horizontalScroll(rememberScrollState()),
			horizontalArrangement = Arrangement.spacedBy(4.dp),
		) {
			tagList.forEach { tag ->
				Box(
					modifier = Modifier
						.clip(RoundedCornerShape(8.dp))
						.background(MapisodeTheme.colorScheme.filledButtonEnableBackground)
						.padding(horizontal = 8.dp, vertical = 4.dp),
				) {
					Row(verticalAlignment = Alignment.CenterVertically) {
						MapisodeText(
							text = tag,
						)
						MapisodeIconButton(
							onClick = {
								tagList.remove(tag)
								onTagChange(tagList)
							},
						) {
							MapisodeIcon(
								id = R.drawable.ic_clear,
								iconSize = IconSize.ExtraSmall,
							)
						}
					}
				}
			}
		}

		MapisodeTextField(
			value = text,
			onValueChange = { newText ->
				if (newText.endsWith(" ")) {
					tagList.add(newText.trim())
					onTagChange(tagList)
					text = ""
				} else {
					text = newText
				}
			},
			placeholder = "태그를 입력해주세요",
		)
	}
}