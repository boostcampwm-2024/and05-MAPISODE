package com.boostcamp.mapisode.ui.photopicker.permissionHandler

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun CameraPermissionHandler(
	onPermissionGranted: () -> Unit,
	onPermissionDenied: () -> Unit,
) {
	val context = LocalContext.current
	val launcher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.RequestPermission(),
	) { isGranted ->
		if (isGranted) {
			onPermissionGranted()
		} else {
			onPermissionDenied()
		}
	}

	LaunchedEffect(Unit) {
		val hasPermission = ContextCompat.checkSelfPermission(
			context,
			Manifest.permission.CAMERA,
		) == PackageManager.PERMISSION_GRANTED

		if (hasPermission) {
			onPermissionGranted()
		} else {
			launcher.launch(Manifest.permission.CAMERA)
		}
	}
}
