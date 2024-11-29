package com.boostcamp.mapisode.ui.photopicker

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.ui.photopicker.component.CameraItem
import com.boostcamp.mapisode.ui.photopicker.component.PhotoItem
import com.boostcamp.mapisode.ui.photopicker.model.PhotoInfo
import com.boostcamp.mapisode.ui.photopicker.permissionHandler.CameraPermissionHandler
import com.boostcamp.mapisode.ui.photopicker.permissionHandler.PhotoPermissionHandler
import java.time.Instant
import java.util.Date

@Composable
fun MapisodePhotoPicker(
	numOfPhoto: Int,
	onPhotoSelected: (List<PhotoInfo>) -> Unit,
	onBackPressed: () -> Unit,
	modifier: Modifier = Modifier.fillMaxSize(),
	isCameraNeeded: Boolean = true,
) {
	val context = LocalContext.current
	var photos by remember { mutableStateOf<List<PhotoInfo>>(emptyList()) }
	var isCameraAvailable: Boolean by rememberSaveable { mutableStateOf(false) }
	var isPermissionGranted by rememberSaveable { mutableStateOf(false) }
	val addedPhotos = remember { mutableStateListOf<PhotoInfo>() }

	val photoLoader = remember { PhotoLoader(context) }

	LaunchedEffect(isPermissionGranted) {
		if (isPermissionGranted) {
			try {
				photos = photoLoader.loadPhotos(100)
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}

	if (!isPermissionGranted) {
		PhotoPermissionHandler(
			onPermissionGranted = {
				isPermissionGranted = true
			},
			onPermissionDenied = {
				isPermissionGranted = false
			},
		)
	}

	if (isCameraNeeded) {
		CameraPermissionHandler(
			onPermissionGranted = {
				isCameraAvailable = true
			},
			onPermissionDenied = {
				isCameraAvailable = false
			},
		)
	}

	val cameraLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.TakePicturePreview(),
	) { bitmap ->
		val uri = PhotoSaver.savePhoto(context, bitmap)
		bitmap?.let {
			addedPhotos.add(PhotoInfo(uri = uri.toString(), dateTaken = Date.from(Instant.now())))
		}
	}

	PhotoPicker(
		photos = photos,
		isCameraNeeded = isCameraNeeded,
		isCameraAvailable = isCameraAvailable,
		addedPhotos = addedPhotos,
		numOfPhoto = numOfPhoto,
		onPhotoSelected = { selectedPhotos ->
			onPhotoSelected(selectedPhotos)
		},
		onBackPressed = onBackPressed,
		onCameraClick = {
			if (isCameraNeeded && isCameraAvailable) {
				cameraLauncher.launch()
			}
		},
		modifier = modifier,
	)
}

@Composable
fun PhotoPicker(
	photos: List<PhotoInfo>,
	onPhotoSelected: (List<PhotoInfo>) -> Unit,
	onBackPressed: () -> Unit,
	isCameraNeeded: Boolean,
	isCameraAvailable: Boolean,
	addedPhotos: List<PhotoInfo> = emptyList(),
	numOfPhoto: Int,
	onCameraClick: () -> Unit,
	modifier: Modifier = Modifier,
) {
	val selectedPhotos = remember { mutableStateListOf<PhotoInfo>() }
	selectedPhotos.addAll(addedPhotos)

	MapisodeScaffold(
		modifier = Modifier.fillMaxSize(),
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = ("사진 선택 ( ${selectedPhotos.size} / $numOfPhoto )"),
				navigationIcon = {
					MapisodeIconButton(
						onClick = onBackPressed,
					) {
						MapisodeIcon(
							id = com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_back_ios,
						)
					}
				},
				actions = {
					MapisodeIconButton(
						onClick = {
							onPhotoSelected(selectedPhotos)
						},
						enabled = selectedPhotos.size > 0,
					) {
						Text("완료")
					}
				},
			)
		},
	) { innerPadding ->
		LazyVerticalGrid(
			columns = GridCells.Fixed(3),
			modifier = modifier
				.padding(innerPadding)
				.padding(8.dp),
			contentPadding = PaddingValues(4.dp),
		) {
			if (isCameraNeeded && isCameraAvailable) {
				item {
					CameraItem(
						onCameraClick = {
							if (selectedPhotos.size < numOfPhoto) {
								onCameraClick()
							}
						},
						modifier = Modifier.padding(4.dp),
					)
				}
			}

			items(addedPhotos) { photo ->
				PhotoItem(
					photo = photo,
					checked = selectedPhotos.contains(photo),
					onPhotoClick = {
						if (selectedPhotos.contains(photo)) {
							selectedPhotos.remove(photo)
						} else if (selectedPhotos.size < numOfPhoto) {
							selectedPhotos.add(photo)
						}
					},
					modifier = Modifier.padding(4.dp),
				)
			}

			items(photos) { photo ->
				PhotoItem(
					photo = photo,
					checked = selectedPhotos.contains(photo),
					onPhotoClick = {
						if (selectedPhotos.contains(photo)) {
							selectedPhotos.remove(photo)
						} else if (selectedPhotos.size < numOfPhoto) {
							selectedPhotos.add(photo)
						}
					},
					modifier = Modifier.padding(4.dp),
				)
			}
		}
	}
}


@Preview
@Composable
fun CircleWithNumberPreview() {
	PhotoPicker(
		photos = listOf(
			PhotoInfo(
				uri = "https://picsum.photos/200",
				dateTaken = null,
			),
		),
		onPhotoSelected = {},
		onBackPressed = {},
		isCameraNeeded = true,
		isCameraAvailable = true,
		numOfPhoto = 5,
		onCameraClick = {},
		modifier = Modifier.fillMaxSize(),
	)
}