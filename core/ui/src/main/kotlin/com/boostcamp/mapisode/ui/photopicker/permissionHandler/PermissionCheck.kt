package com.boostcamp.mapisode.ui.photopicker.permissionHandler

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

class PermissionCheck(private val context: Context) {
	companion object {
		private val STORAGE_PERMISSIONS =
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
			} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
			} else {
				arrayOf(
					Manifest.permission.READ_EXTERNAL_STORAGE,
					Manifest.permission.WRITE_EXTERNAL_STORAGE,
				)
			}

		private val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)

		fun getRequiredPermissions(isCameraNeeded: Boolean): Array<String> {
			return if (isCameraNeeded) {
				STORAGE_PERMISSIONS + CAMERA_PERMISSION
			} else {
				STORAGE_PERMISSIONS
			}
		}
	}

	fun hasPermissions(isCameraNeeded: Boolean): Boolean {
		return getRequiredPermissions(isCameraNeeded).all {
			ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
		}
	}
}

