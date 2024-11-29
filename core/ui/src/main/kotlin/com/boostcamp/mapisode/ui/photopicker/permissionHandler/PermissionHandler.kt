package com.boostcamp.mapisode.ui.photopicker.permissionHandler

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun PermissionHandler(
	isCameraNeeded: Boolean,
	onPermissionsGranted: () -> Unit,
	onPermissionsDenied: () -> Unit,
) {
	val context = LocalContext.current
	var hasPermissions by rememberSaveable {
		mutableStateOf(PermissionCheck(context).hasPermissions(isCameraNeeded))
	}

	val launcher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.RequestMultiplePermissions(),
	) { permissions ->
		val allGranted = permissions.values.all { it }
		hasPermissions = allGranted

		if (allGranted) {
			onPermissionsGranted()
		} else {
			onPermissionsDenied()
		}
	}

	LaunchedEffect(hasPermissions) {
		if (!hasPermissions) {
			launcher.launch(PermissionCheck.getRequiredPermissions(isCameraNeeded))
		} else {
			onPermissionsGranted()
		}
	}
}
