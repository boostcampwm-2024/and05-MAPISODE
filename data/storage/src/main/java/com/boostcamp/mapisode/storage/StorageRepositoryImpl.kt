package com.boostcamp.mapisode.storage

import androidx.core.net.toUri
import com.boostcamp.mapisode.firebase.firestore.StorageConstants.PATH_IMAGES
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
	private val storage: FirebaseStorage,
) : StorageRepository {

	override suspend fun uploadSingleImageToStorage(
		imageUri: String,
		uid: String,
	): String {
		val imageRef = storage.reference.child("$PATH_IMAGES/$uid/0")
		return try {
			val uploadTask = imageRef.putFile(imageUri.toUri()).await()
			val downloadUrl = uploadTask.task.result.storage.downloadUrl.await()
			downloadUrl.toString()
		} catch (e: Exception) {
			throw e
		}
	}

}
