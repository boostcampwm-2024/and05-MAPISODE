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
	// 위치 권한 설정을 감시하고, 권한이 없으면 요청한다.
	LaunchedEffect(uiState.isLocationPermissionGranted) {
		val isFineLocationGranted = ContextCompat.checkSelfPermission(
			context,
			Manifest.permission.ACCESS_FINE_LOCATION,
		) == PackageManager.PERMISSION_GRANTED

		if (!uiState.isLocationPermissionGranted && !isFineLocationGranted) {
			// 위치 권한이 허용되지 않은 경우 권한을 요청
			launcherLocationPermissions.launch(
				arrayOf(
					Manifest.permission.ACCESS_FINE_LOCATION,
				),
			)
		} else if (isFineLocationGranted) {
			// 위치 권한이 허용된 경우 권한 상태를 업데이트
			updatePermission(true)
		}
	}
}

@Composable
fun getPermissionsLauncher(
	updatePermission: (Boolean) -> Unit,
): ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> =
	rememberLauncherForActivityResult(
		ActivityResultContracts.RequestMultiplePermissions(),
	) { permissions ->
		val isGranted = permissions.values.all { it }
		updatePermission(isGranted)
	}
