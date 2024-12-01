package com.boostcamp.mapisode.ui.photopicker

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PhotoSaver {
	fun savePhoto(context: Context, bitmap: Bitmap?): Uri? {
		if (bitmap == null) return null

		val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
		val imageFileName = "JPEG_$timestamp"

		val contentValues = ContentValues().apply {
			put(MediaStore.MediaColumns.DISPLAY_NAME, imageFileName)
			put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
				put(MediaStore.Images.Media.IS_PENDING, 1)
			}
		}

		val contentResolver = context.contentResolver
		val imageUri = contentResolver.insert(
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
			contentValues,
		) ?: throw IOException("사진 저장에 실패했습니다.")
		
		try {
			contentResolver.openOutputStream(imageUri)?.use { outputStream ->
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
			}

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				contentValues.clear()
				contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
				contentResolver.update(imageUri, contentValues, null, null)
			}

			return imageUri
		} catch (e: IOException) {
			contentResolver.delete(imageUri, null, null)
			throw IOException("사진 저장에 실패했습니다.")
		}
	}
}
