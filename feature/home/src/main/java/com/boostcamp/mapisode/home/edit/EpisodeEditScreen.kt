package com.boostcamp.mapisode.home.edit

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
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
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeImageButton
import com.boostcamp.mapisode.designsystem.compose.datepicker.MapisodeDatePicker
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenu
import com.boostcamp.mapisode.designsystem.compose.menu.MapisodeDropdownMenuItem
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTextStyle
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.ui.photopicker.MapisodePhotoPicker
import com.boostcamp.mapisode.ui.photopicker.model.PhotoInfo
import com.naver.maps.map.CameraPosition
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import java.time.ZoneId
import java.util.Date

@Composable
fun EpisodeEditRoute(
	episodeId: String,
	viewModel: EpisodeEditViewModel = hiltViewModel(),
	onBackClick: () -> Unit = {},
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val context = LocalContext.current

	BackHandler {
		if (uiState.isSelectingPicture || uiState.isSelectingLocation) {
			viewModel.onIntent(EpisodeEditIntent.OnBackClickToEditScreen)
		} else {
			viewModel.onIntent(EpisodeEditIntent.OnBackClickToOutOfEditScreen)
		}
	}

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

				is EpisodeEditSideEffect.NavigateBackScreen -> {
					onBackClick()
				}
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
	} else {
		EpisodeEditScreen(
			state = uiState,
			onPictureClick = { viewModel.onIntent(EpisodeEditIntent.OnPictureClick) },
			onLocationClick = { viewModel.onIntent(EpisodeEditIntent.OnLocationClick(it)) },
			onEditClick = { viewModel.onIntent(EpisodeEditIntent.OnEditClick(it)) },
			onBackClick = onBackClick,
		)
		if (uiState.isSelectingPicture) {
			PictureSelectionScreen(
				onPictureClick = { viewModel.onIntent(EpisodeEditIntent.OnPictureClick) },
				onPickPhotos = { viewModel.onIntent(EpisodeEditIntent.OnSetPictures(it)) },
			)
		} else if (uiState.isSelectingLocation) {
			LocationSelectionScreen(
				episodeAddress = uiState.episode.searchedAddress,
				cameraPosition = CameraPosition(
					uiState.episode.location.toLatLng(),
					16.0,
				),
				onSetEpisodeLocation = { viewModel.onIntent(EpisodeEditIntent.OnSetLocation(it)) },
				onRequestSelection = { viewModel.onIntent(EpisodeEditIntent.OnRequestSelection(it)) },
				onDismissSelection = { viewModel.onIntent(EpisodeEditIntent.OnDismissSelection) },
			)
		}

		if (uiState.isEditingInProgress) {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(MapisodeTheme.colorScheme.scrim),
				contentAlignment = Alignment.Center,
			) {
				MapisodeCircularLoadingIndicator()
			}
		}
	}
}

@Composable
fun EpisodeEditScreen(
	state: EpisodeEditState,
	modifier: Modifier = Modifier,
	onPictureClick: () -> Unit,
	onLocationClick: (EpisodeLatLng) -> Unit,
	onEditClick: (EpisodeEditInfo) -> Unit,
	onBackClick: () -> Unit = {},
) {
	var title by rememberSaveable { mutableStateOf(state.episode.title) }
	var description by rememberSaveable { mutableStateOf(state.episode.content) }
	var group by rememberSaveable { mutableStateOf(state.episode.group) }
	var category by rememberSaveable {
		mutableStateOf(CategoryMapper.mapToCategoryName(state.episode.category))
	}
	var tag by rememberSaveable { mutableStateOf(state.episode.tags) }
	var date by rememberSaveable {
		mutableStateOf(state.episode.memoryDate)
	}
	var isGroupMenuPoppedUp by remember { mutableStateOf(false) }
	var isCategoryMenuPoppedUp by remember { mutableStateOf(false) }
	var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
	var contentWidth by remember { mutableIntStateOf(0) }

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
				modifier = Modifier
					.fillMaxWidth()
					.onGloballyPositioned { layoutCoordinates ->
						contentWidth = layoutCoordinates.size.width
					},
				contentAlignment = Alignment.Center,
			) {
				Row(
					modifier = Modifier
						.wrapContentWidth()
						.horizontalScroll(rememberScrollState()),
					horizontalArrangement = Arrangement.spacedBy(10.dp),
					verticalAlignment = Alignment.CenterVertically,
				) {
					state.episode.serverImageUrl.forEach { imageUrl ->
						AsyncImage(
							model = imageUrl,
							contentDescription = "애피소드 이미지",
							modifier = Modifier
								.size(110.dp)
								.clip(RoundedCornerShape(16.dp)),
							contentScale = ContentScale.Crop,
						)
					}
					state.episode.localImageUrl.forEach { imageUrl ->
						AsyncImage(
							model = imageUrl.toString(),
							contentDescription = "애피소드 이미지",
							modifier = Modifier
								.size(110.dp)
								.clip(RoundedCornerShape(8.dp)),
							contentScale = ContentScale.Crop,
						)
					}
					MapisodeImageButton(
						onClick = onPictureClick,
						showImage = true,
						modifier = Modifier.size(110.dp),
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

			MapisodeTextField(
				modifier = modifier.fillMaxWidth(),
				value = state.episode.selectedAddress,
				readOnly = true,
				placeholder = "장소를 입력해주세요",
				isError = state.episode.selectedAddress.isEmpty(),
				onValueChange = {},
				trailingIcon = {
					MapisodeIconButton(
						onClick = {
							onLocationClick(
								state.episode.location,
							)
						},
					) {
						MapisodeIcon(
							id = R.drawable.ic_location,
							iconSize = IconSize.ExtraSmall,
						)
					}
				},
			)

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
				modifier = modifier
					.fillMaxWidth()
					.aspectRatio(3f),
				value = description,
				onValueChange = { descriptionText -> description = descriptionText },
				placeholder = "",
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

			Column {
				MapisodeTextField(
					modifier = modifier
						.fillMaxWidth()
						.clickable {},
					value = group,
					onValueChange = { groupText -> group = groupText },
					readOnly = true,
					placeholder = "그룹을 입력해주세요",
					isError = group.isEmpty(),
					trailingIcon = {
						MapisodeIconButton(
							onClick = { isGroupMenuPoppedUp = true },
						) {
							MapisodeIcon(
								id = R.drawable.ic_arrow_drop_down,
							)
						}
					},
				)

				MapisodeDropdownMenu(
					expanded = isGroupMenuPoppedUp,
					onDismissRequest = { isGroupMenuPoppedUp = false },
					modifier = Modifier.width(with(LocalDensity.current) { contentWidth.toDp() }),
				) {
					state.groups.forEach {
						MapisodeDropdownMenuItem(
							onClick = {
								group = it.name
								isGroupMenuPoppedUp = false
							},
						) {
							MapisodeText(
								text = it.name,
							)
						}
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

			Column {
				MapisodeTextField(
					modifier = modifier
						.fillMaxWidth()
						.clickable { isCategoryMenuPoppedUp = true },
					value = category,
					onValueChange = { categoryText -> category = categoryText },
					readOnly = true,
					placeholder = "카테고리를 입력해주세요",
					isError = category.isEmpty(),
					trailingIcon = {
						MapisodeIconButton(
							onClick = { isCategoryMenuPoppedUp = true },
						) {
							MapisodeIcon(
								id = R.drawable.ic_arrow_drop_down,
							)
						}
					},
				)

				MapisodeDropdownMenu(
					expanded = isCategoryMenuPoppedUp,
					onDismissRequest = { isCategoryMenuPoppedUp = false },
					modifier = Modifier.width(with(LocalDensity.current) { contentWidth.toDp() }),
				) {
					persistentListOf("볼거리", "먹거리", "나머지").forEach {
						MapisodeDropdownMenuItem(
							onClick = {
								category = it
								isCategoryMenuPoppedUp = false
							},
						) {
							MapisodeText(
								text = it,
							)
						}
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

			Spacer(modifier = Modifier.height(8.dp))

			TagInputField(
				tagList = state.episode.tags,
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
					.height(48.dp),
				onClick = { showDatePickerDialog = true },
				showImage = false,
				imageContent = {
					MapisodeText(
						text = date.toFormattedString(),
					)
				},
			)

			if (showDatePickerDialog) {
				MapisodeDatePicker(
					onDismissRequest = { showDatePickerDialog = false },
					onDateSelected = { localDate ->
						date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
						showDatePickerDialog = false
					},
					initialDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
				)
			}

			Spacer(modifier = Modifier.height(20.dp))

			MapisodeFilledButton(
				modifier = modifier
					.fillMaxWidth()
					.height(52.dp),
				onClick = {
					onEditClick(
						EpisodeEditInfo(
							id = state.episode.id,
							category = CategoryMapper.mapToCategory(category),
							content = description,
							group = state.groups.first { grp -> grp.name == group }.id,
							serverImageUrl = state.episode.serverImageUrl,
							localImageUrl = state.episode.localImageUrl,
							selectedAddress = state.episode.selectedAddress,
							location = state.episode.location,
							memoryDate = date,
							tags = tag,
							title = title,
							createdBy = state.episode.createdBy,
						),
					)
				},
				text = "수정하기",
			)

			Spacer(modifier = Modifier.height(20.dp))
		}
	}
}

@Composable
fun PictureSelectionScreen(
	onPictureClick: () -> Unit,
	onPickPhotos: (PersistentList<Uri>) -> Unit,
) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		MapisodePhotoPicker(
			numOfPhoto = 4,
			onPhotoSelected = { photoInfo ->
				onPickPhotos(photoInfo.map { it.uri.toUri() }.toPersistentList())
			},
			onPermissionDenied = onPictureClick,
			onBackPressed = onPictureClick,
			modifier = Modifier.fillMaxSize(),
		)
	}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagInputField(
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
					id = R.drawable.ic_clear,
					modifier = Modifier
						.align(Alignment.CenterVertically)
						.clickable {
							updatedTagList = updatedTagList - tag
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
					updatedTagList = updatedTagList + (newText.trim())
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

@Composable
fun PhotoPickerScreen(
	localSelectedUrls: PersistentList<PhotoInfo>,
	modifier: Modifier = Modifier,
) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		val localSelectedUrl: PersistentList<PhotoInfo> = remember { persistentListOf() }
		MapisodePhotoPicker(
			numOfPhoto = 4,
			onPhotoSelected = { photoInfo ->
				localSelectedUrl + photoInfo
			},
			onPermissionDenied = {},
			onBackPressed = {},
		)
	}
}
