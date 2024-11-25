package com.boostcamp.mapisode.episode

import androidx.core.net.toUri
import com.boostcamp.mapisode.episode.model.EpisodeFirestoreModel
import com.boostcamp.mapisode.episode.model.toFirestoreModel
import com.boostcamp.mapisode.firebase.firestore.FirestoreConstants
import com.boostcamp.mapisode.firebase.firestore.StorageConstants.PATH_IMAGES
import com.boostcamp.mapisode.model.EpisodeLatLng
import com.boostcamp.mapisode.model.EpisodeModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
	private val database: FirebaseFirestore,
	private val storage: FirebaseStorage,
) : EpisodeRepository {
	private val episodeCollection = database.collection(FirestoreConstants.COLLECTION_EPISODE)
	private val groupCollection = database.collection(FirestoreConstants.COLLECTION_GROUP)

	override suspend fun getEpisodesByGroup(groupId: String): List<EpisodeModel> {
		val query = episodeCollection
			.whereEqualTo(
				FirestoreConstants.FIELD_GROUP,
				groupCollection.document(groupId),
			)
		val querySnapshot = query.get().await()
		if (querySnapshot.isEmpty) {
			return emptyList()
		}

		return try {
			querySnapshot.toDomainModelList()
		} catch (e: Exception) {
			Timber.tag("getEpisodesByGroup")
				.e(e)
			throw e
		}
	}

	override suspend fun getEpisodesByGroupAndLocation(
		groupId: String,
		start: EpisodeLatLng,
		end: EpisodeLatLng,
		category: String?,
	): List<EpisodeModel> {
		var query = episodeCollection
			.whereEqualTo(FirestoreConstants.FIELD_GROUP, groupCollection.document(groupId))
			.whereGreaterThanOrEqualTo(
				FirestoreConstants.FIELD_LOCATION,
				GeoPoint(start.latitude, start.longitude),
			)
			.whereLessThanOrEqualTo(
				FirestoreConstants.FIELD_LOCATION,
				GeoPoint(end.latitude, end.longitude),
			)

		category?.let {
			query = query.whereEqualTo(FirestoreConstants.FIELD_CATEGORY, it)
		}

		val querySnapshot = query.get().await()

		if (querySnapshot.isEmpty) {
			return emptyList()
		}

		return try {
			querySnapshot.toDomainModelList()
		} catch (e: Exception) {
			Timber.tag("getEpisodesByGroupAndLocation")
				.e(e)
			throw e
		}
	}

	override suspend fun getEpisodesByGroupAndCategory(
		groupId: String,
		category: String,
	): List<EpisodeModel> {
		val query = episodeCollection
			.whereEqualTo(FirestoreConstants.FIELD_GROUP, groupCollection.document(groupId))
			.whereEqualTo(FirestoreConstants.FIELD_CATEGORY, category)
		val querySnapshot = query.get().await()

		if (querySnapshot.isEmpty) {
			return emptyList()
		}

		return try {
			querySnapshot.toDomainModelList()
		} catch (e: Exception) {
			Timber.tag("getEpisodesByGroupAndLocation")
				.e(e)
			throw e
		}
	}

	override suspend fun getEpisodeById(episodeId: String): EpisodeModel? =
		episodeCollection.document(episodeId)
			.get()
			.await()
			.toObject(EpisodeFirestoreModel::class.java)
			?.toDomainModel(episodeId)

	override suspend fun createEpisode(episodeModel: EpisodeModel): String {
		val newEpisodeId = UUID.randomUUID().toString().replace("-", "")
		Timber.d(episodeModel.imageUrls.toString())
		val uploadedImageUrls = uploadImagesToStorage(newEpisodeId, episodeModel.imageUrls)
		Timber.d(uploadedImageUrls.toString())
		return try {
			episodeCollection
				.document(newEpisodeId)
				.set(episodeModel.toFirestoreModel(database, uploadedImageUrls))
				.await()
			newEpisodeId
		} catch (e: Exception) {
			Timber.tag("createEpisode")
				.e(e)
			throw e
		}
	}

	private suspend fun uploadImagesToStorage(
		newEpisodeId: String,
		imageUris: List<String>,
	): List<String> {
		val imageStorageUrls = mutableListOf<String>()
		imageUris.forEachIndexed { index, imageUri ->
			val imageRef =
				storage.reference.child("$PATH_IMAGES/$newEpisodeId/${index + 1}")
			imageRef.putFile(imageUri.toUri())
				.addOnCompleteListener { task ->
					if (task.isSuccessful) {
						CoroutineScope(Dispatchers.IO).launch {
							val downloadUrl = imageRef.downloadUrl.await()
							Timber.d("Image URL: $downloadUrl")
						}
					} else {
						Timber.e(task.exception)
						throw task.exception ?: RuntimeException("Image upload failed")
					}
				}.await()
			imageStorageUrls.add(imageRef.downloadUrl.await().toString())
		}

		return imageStorageUrls
	}

	private fun QuerySnapshot.toDomainModelList(): List<EpisodeModel> =
		documents.map { document ->
			val model = requireNotNull(document.toObject(EpisodeFirestoreModel::class.java))
			model.toDomainModel(document.id)
		}
}
