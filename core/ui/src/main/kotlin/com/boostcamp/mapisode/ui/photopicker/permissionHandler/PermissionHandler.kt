package com.boostcamp.mapisode.ui.photopicker.permissionHandler

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun PermissionHandler(
	isCameraNeeded: Boolean,
	onPermissionsGranted: () -> Unit,
	onPermissionsDenied: () -> Unit,
) {
	val context = LocalContext.current
	var hasPermissions by rememberSaveable {
		mutableStateOf(PermissionCheck(context).arePermissionsGranted(isCameraNeeded))
	}
	var showPermissionDialog by rememberSaveable { mutableStateOf(false) }
	var deniedPermissionString by rememberSaveable { mutableStateOf("") }

	val launcher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.RequestMultiplePermissions(),
	) { permissions ->
		val allGranted = permissions.values.all { it }
		hasPermissions = allGranted

		if (allGranted) {
			onPermissionsGranted()
		} else {
			val deniedPermission = permissions.filter { !it.value }.keys.map {
				when (it) {
					android.Manifest.permission.CAMERA -> "카메라"
					else -> "저장소"
				}
			}.toSet().joinToString(", ")
			deniedPermissionString = deniedPermission
			showPermissionDialog = true
		}
	}

	LaunchedEffect(hasPermissions) {
		if (!hasPermissions) {
			launcher.launch(PermissionCheck.getRequiredPermissions(isCameraNeeded))
		} else {
			onPermissionsGranted()
		}
	}

	if (showPermissionDialog) {
		AlertDialog(
			onDismissRequest = { showPermissionDialog = false },
			title = {
				MapisodeText(
					text = "권한 필요",
					style = MapisodeTheme.typography.titleLarge,
				)
			},
			text = {
				MapisodeText(
					text = "앱의 원할한 사용을 위해서는 $deniedPermissionString 접근 권한이 필요합니다. 다시 설정하시겠습니까?",
					style = MapisodeTheme.typography.bodyLarge,
				)
			},
			confirmButton = {
				MapisodeFilledButton(
					onClick = {
						showPermissionDialog = false
						context.startActivity(
							Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
								data = Uri.fromParts("package", context.packageName, null)
							},
						)
						onPermissionsDenied()
					},
					text = "확인",
				)
			},
			dismissButton = {
				MapisodeFilledButton(
					onClick = {
						showPermissionDialog = false
						onPermissionsDenied()
					},
					text = "취소",
				)
			},
		)
	}
}
