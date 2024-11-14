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

	override suspend fun getEpisodesByGroup(groupId: String): List<EpisodeDTO> {
		val query = episodeCollection
			.whereEqualTo(
				"group",
				groupCollection.document(groupId),
			)
		val querySnapshot = query.get().await()
		if (querySnapshot.isEmpty) {
			Timber.tag("getEpisodesByGroup")
				.d("No such document")
			return emptyList()
		}

		return try {
			querySnapshot.toDTOList()
		} catch (e: Exception) {
			Timber.tag("getEpisodesByGroup")
				.e(e)
			emptyList()
		}
	}

	override suspend fun getEpisodesByGroupAndLocation(
		groupId: String,
		start: Pair<Double, Double>,
		end: Pair<Double, Double>,
	): List<EpisodeDTO> {
		val query = episodeCollection
			.whereEqualTo("group", groupCollection.document(groupId))
			.whereGreaterThanOrEqualTo("location", GeoPoint(start.first, start.second))
			.whereLessThanOrEqualTo("location", GeoPoint(end.first, end.second))
		val querySnapshot = query.get().await()

		if (querySnapshot.isEmpty) {
			Timber.tag("getEpisodesByGroupAndLocation")
				.d("No such document")
			return emptyList()
		}

		return try {
			querySnapshot.toDTOList()
		} catch (e: Exception) {
			Timber.tag("getEpisodesByGroupAndLocation")
				.e(e)
			emptyList()
		}
	}

	override suspend fun getEpisodesByGroupAndCategory(
		groupId: String,
		category: String,
	): List<EpisodeDTO> {
		val query = episodeCollection
			.whereEqualTo("group", groupCollection.document(groupId))
			.whereEqualTo("category", category)
		val querySnapshot = query.get().await()

		if (querySnapshot.isEmpty) {
			Timber.tag("getEpisodesByGroupAndLocation")
				.d("No such document")
			return emptyList()
		}

		return try {
			querySnapshot.toDTOList()
		} catch (e: Exception) {
			Timber.tag("getEpisodesByGroupAndLocation")
				.e(e)
			emptyList()
		}
	}

	override suspend fun getEpisodeById(episodeId: String): EpisodeDTO? {
		return episodeCollection.document(episodeId)
			.get()
			.await()
			.toObject(EpisodeFirestoreModel::class.java)
			?.toDTO(episodeId)
	}

	override suspend fun createEpisode(episodeDTO: EpisodeDTO): String {
		val newEpisodeId = UUID.randomUUID().toString().replace("-", "")
		return try {
			episodeCollection.document(newEpisodeId).set(episodeDTO.toFirestoreModel(database))
				.await()
			newEpisodeId
		} catch (e: Exception) {
			Timber.tag("createEpisode")
				.e(e)
			throw e
		}
	}

	private fun QuerySnapshot.toDTOList(): List<EpisodeDTO> {
		return documents.map { document ->
			val model = requireNotNull(document.toObject(EpisodeFirestoreModel::class.java))
			model.toDTO(document.id)
		}
	}
}
