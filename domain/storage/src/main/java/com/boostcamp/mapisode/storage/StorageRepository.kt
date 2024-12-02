package com.boostcamp.mapisode.storage

interface StorageRepository {
	suspend fun uploadSingleImageToStorage(imageUri: String, uid: String): String
}
