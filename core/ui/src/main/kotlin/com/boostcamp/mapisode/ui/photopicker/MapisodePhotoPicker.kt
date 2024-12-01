package com.boostcamp.mapisode.ui.photopicker

import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.ui.photopicker.component.CameraItem
import com.boostcamp.mapisode.ui.photopicker.component.PhotoItem
import com.boostcamp.mapisode.ui.photopicker.model.PhotoInfo
import com.boostcamp.mapisode.ui.photopicker.permissionHandler.PermissionHandler
import java.time.Instant
import java.util.Date

@Composable
fun MapisodePhotoPicker(
	numOfPhoto: Int,
	onPhotoSelected: (List<PhotoInfo>) -> Unit,
	onPermissionDenied: () -> Unit,
	onBackPressed: () -> Unit,
	modifier: Modifier = Modifier,
	isCameraNeeded: Boolean = true,
) {
	val context = LocalContext.current
	var photos by rememberSaveable { mutableStateOf<List<PhotoInfo>>(emptyList()) }
	var isPermissionsGranted by rememberSaveable { mutableStateOf(false) }
	val addedPhoto = rememberSaveable { mutableStateOf<PhotoInfo?>(null) }
	val cameraPhotos = rememberMutableStateListOf<PhotoInfo>()

	val photoLoader = remember(context) { PhotoLoader(context) }

	LaunchedEffect(isPermissionsGranted) {
		if (isPermissionsGranted) {
			try {
				photos = photoLoader.loadPhotos(100)
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}

	PermissionHandler(
		isCameraNeeded = isCameraNeeded,
		onPermissionsGranted = {
			isPermissionsGranted = true
		},
		onPermissionsDenied = {
			isPermissionsGranted = false
			onPermissionDenied()
		},
	)

	val cameraLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.TakePicturePreview(),
	) { bitmap ->
		val uri = PhotoSaver.savePhoto(context, bitmap)
		bitmap?.let {
			val photo = PhotoInfo(uri = uri.toString(), dateTaken = Date.from(Instant.now()))
			addedPhoto.value = photo
			cameraPhotos.add(photo)
		}
	}

	PhotoPicker(
		photos = cameraPhotos.reversed() + photos,
		isCameraNeeded = isCameraNeeded,
		isCameraAvailable = isPermissionsGranted,
		addedPhoto = addedPhoto.value,
		numOfPhoto = numOfPhoto,
		onPhotoSelected = { selectedPhotos ->
			onPhotoSelected(selectedPhotos)
		},
		onBackPressed = onBackPressed,
		onCameraClick = {
			if (isCameraNeeded && isPermissionsGranted) {
				cameraLauncher.launch()
			}
		},
		clickDenialToast = {
			Toast.makeText(context, "최대 $numOfPhoto 개의 사진만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
		},
		modifier = modifier.fillMaxSize(),
	)
}

@Composable
fun PhotoPicker(
	photos: List<PhotoInfo>,
	onPhotoSelected: (List<PhotoInfo>) -> Unit,
	onBackPressed: () -> Unit,
	isCameraNeeded: Boolean,
	isCameraAvailable: Boolean,
	addedPhoto: PhotoInfo?,
	numOfPhoto: Int,
	onCameraClick: () -> Unit,
	clickDenialToast: () -> Unit,
	modifier: Modifier = Modifier,
) {
	val selectedPhotos = rememberMutableStateListOf<PhotoInfo>()
	if (addedPhoto != null) selectedPhotos.add(addedPhoto)

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
							} else {
								clickDenialToast()
							}
						},
						modifier = Modifier.padding(4.dp),
					)
				}
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
						} else {
							clickDenialToast()
						}
					},
					modifier = Modifier.padding(4.dp),
				)
			}
		}
	}
}
