package com.boostcamp.mapisode.mygroup

import com.boostcamp.mapisode.firebase.firestore.FirestoreConstants
import com.boostcamp.mapisode.model.GroupModel
import com.boostcamp.mapisode.mygroup.model.GroupFirestoreModel
import com.boostcamp.mapisode.mygroup.model.toDomainModel
import com.boostcamp.mapisode.mygroup.model.toFirestoreModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(private val database: FirebaseFirestore) :
	GroupRepository {
	private val groupCollection = database.collection(FirestoreConstants.COLLECTION_GROUP)

	override suspend fun getGroupById(groupId: String): GroupModel? =
		groupCollection.document(groupId)
			.get()
			.await()
			.toObject(GroupFirestoreModel::class.java)
			?.toDomainModel(groupId)

	override suspend fun getAllGroups(): List<GroupModel> {
		val querySnapshot = groupCollection.get().await()
		if (querySnapshot.isEmpty) {
			return emptyList()
		}

		return try {
			querySnapshot.toDomainModelList()
		} catch (e: Exception) {
			throw e
		}
	}

	override suspend fun createGroup(groupModel: GroupModel): String {
		val newGroupId = UUID.randomUUID().toString().replace("-", "")
		return try {
			groupCollection.document(newGroupId).set(groupModel.toFirestoreModel(database))
				.await()
			newGroupId
		} catch (e: Exception) {
			throw e
		}
	}

	override suspend fun updateGroup(groupId: String, groupModel: GroupModel) {
		try {
			groupCollection.document(groupId).set(groupModel.toFirestoreModel(database))
				.await()
		} catch (e: Exception) {
			throw e
		}
	}

	override suspend fun deleteGroup(groupId: String) {
		try {
			groupCollection.document(groupId).delete().await()
		} catch (e: Exception) {
			throw e
		}
	}

	private fun QuerySnapshot.toDomainModelList(): List<GroupModel> =
		documents.map { document ->
			val model = requireNotNull(document.toObject(GroupFirestoreModel::class.java))
			model.toDomainModel(document.id)
		}
}
