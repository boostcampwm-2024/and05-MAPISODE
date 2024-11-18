package com.boostcamp.mapisode.episode

import com.boostcamp.mapisode.episode.model.EpisodeFirestoreModel
import com.boostcamp.mapisode.episode.model.toFirestoreModel
import com.boostcamp.mapisode.firebase.firestore.FirestoreConstants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
	private val database: FirebaseFirestore,
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
	): List<EpisodeModel> {
		val query = episodeCollection
			.whereEqualTo(FirestoreConstants.FIELD_GROUP, groupCollection.document(groupId))
			.whereGreaterThanOrEqualTo(
				FirestoreConstants.FIELD_LOCATION,
				GeoPoint(start.latitude, start.longitude),
			)
			.whereLessThanOrEqualTo(
				FirestoreConstants.FIELD_LOCATION,
				GeoPoint(end.latitude, end.longitude),
			)
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

	override suspend fun getEpisodeById(episodeId: String): EpisodeModel? {
		return episodeCollection.document(episodeId)
			.get()
			.await()
			.toObject(EpisodeFirestoreModel::class.java)
			?.toDomainModel(episodeId)
	}

	override suspend fun createEpisode(episodeModel: EpisodeModel): String {
		val newEpisodeId = UUID.randomUUID().toString().replace("-", "")
		return try {
			episodeCollection.document(newEpisodeId).set(episodeModel.toFirestoreModel(database))
				.await()
			newEpisodeId
		} catch (e: Exception) {
			Timber.tag("createEpisode")
				.e(e)
			throw e
		}
	}

	private fun QuerySnapshot.toDomainModelList(): List<EpisodeModel> {
		return documents.map { document ->
			val model = requireNotNull(document.toObject(EpisodeFirestoreModel::class.java))
			model.toDomainModel(document.id)
		}
	}
}
