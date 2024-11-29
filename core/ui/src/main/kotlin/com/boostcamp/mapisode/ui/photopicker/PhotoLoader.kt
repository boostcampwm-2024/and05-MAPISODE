package com.boostcamp.mapisode.ui.photopicker

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class PhotoLoader(private val context: Context) {

	suspend fun loadPhotos(
		limit: Int = 100,
		offset: Int = 0,
	): List<PhotoInfo> = withContext(Dispatchers.IO) {
		val photos = mutableListOf<PhotoInfo>()

		val projection = arrayOf(
			MediaStore.Images.Media._ID,
			MediaStore.Images.Media.DATE_TAKEN,
		)

		val selection = "${MediaStore.Images.Media.SIZE} > 0"
		val selectionArgs = null
		val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
		val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

		context.contentResolver.query(
			collection,
			projection,
			selection,
			selectionArgs,
			sortOrder,
		)?.use { cursor ->
			val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
			val dateTakenColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)

			while (cursor.moveToNext()) {
				val id = cursor.getLong(idColumn)
				val dateTaken = cursor.getLong(dateTakenColumn).let {
					if (it > 0) Date(it) else null
				}

				val contentUri = ContentUris.withAppendedId(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					id,
				)

				photos.add(
					PhotoInfo(
						uri = contentUri.toString(),
						dateTaken = dateTaken,
					),
				)
			}
		}

		photos
	}
}

