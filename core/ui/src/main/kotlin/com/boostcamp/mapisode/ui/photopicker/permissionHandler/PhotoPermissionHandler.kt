package com.boostcamp.mapisode.ui.photopicker.permissionHandler

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import timber.log.Timber

class PhotoPermissionCheck(private val context: Context) {
	companion object {
		val REQUIRED_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
		} else {
			arrayOf(
				Manifest.permission.READ_EXTERNAL_STORAGE,
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
			)
		}
	}

	fun hasPermissions(): Boolean {
		return REQUIRED_PERMISSIONS.all {
			ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
		}
	}
}

@Composable
fun PhotoPermissionHandler(
	onPermissionGranted: () -> Unit,
	onPermissionDenied: () -> Unit,
) {
	val context = LocalContext.current
	var hasPermission by remember {
		mutableStateOf(PhotoPermissionCheck(context).hasPermissions())
	}

	val launcher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.RequestMultiplePermissions(),
	) { permissions ->
		val allGranted = permissions.values.all { it }
		hasPermission = allGranted

		if (allGranted) {
			onPermissionGranted()
		} else {
			onPermissionDenied()
		}
	}

	LaunchedEffect(hasPermission) {
		Timber.e("3.hasPermission: $hasPermission")
		if (!hasPermission) {
			launcher.launch(PhotoPermissionCheck.REQUIRED_PERMISSIONS)
		} else {
			onPermissionGranted()
		}
	}
}

