package com.boostcamp.mapisode.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat

@Composable
fun HomePermissionHandler(
	context: Context,
	uiState: HomeState,
	launcherLocationPermissions: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
	updatePermission: (Boolean) -> Unit,
) {
	LaunchedEffect(uiState.isLocationPermissionGranted) {
		val isFineLocationGranted = ContextCompat.checkSelfPermission(
			context,
			Manifest.permission.ACCESS_FINE_LOCATION,
		) == PackageManager.PERMISSION_GRANTED

		if (!uiState.isLocationPermissionGranted && !isFineLocationGranted) {
			launcherLocationPermissions.launch(
				arrayOf(
					Manifest.permission.ACCESS_FINE_LOCATION,
				),
			)
		} else if (isFineLocationGranted) {
			updatePermission(true)
		}
	}
}

@Composable
fun getPermissionsLauncher(
	updatePermission: (Boolean) -> Unit,
): ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> {
	return rememberLauncherForActivityResult(
		ActivityResultContracts.RequestMultiplePermissions(),
	) { permissions ->
		val isGranted = permissions.values.all { it }
		updatePermission(isGranted)
	}
}
