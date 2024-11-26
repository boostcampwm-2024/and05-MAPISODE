package com.boostcamp.mapisode.mygroup

import com.boostcamp.mapisode.firebase.firestore.FirestoreConstants
import com.boostcamp.mapisode.model.GroupModel
import com.boostcamp.mapisode.mygroup.model.GroupFirestoreModel
import com.boostcamp.mapisode.mygroup.model.toDomainModel
import com.boostcamp.mapisode.mygroup.model.toFirestoreModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(private val database: FirebaseFirestore) :
	GroupRepository {
	private val groupCollection = database.collection(FirestoreConstants.COLLECTION_GROUP)
	private val userCollection = database.collection(FirestoreConstants.COLLECTION_USER)
	private val inviteCodesCollection = database.collection(FirestoreConstants.COLLECTION_INVITE_CODES)

	override suspend fun getGroupByGroupId(groupId: String): GroupModel = try {
		groupCollection.document(groupId)
			.get()
			.await()
			.toObject(GroupFirestoreModel::class.java)?.toDomainModel(groupId)
			?: throw Exception("그룹을 찾을 수 없습니다.")
	} catch (e: Exception) {
		throw e
	}

	override suspend fun getGroupsByUserId(userId: String): List<GroupModel> = try {
		val userSnapshot = userCollection.document(userId).get().await()

		@Suppress("UNCHECKED_CAST")
		val groupReferences = (userSnapshot[FirestoreConstants.FIELD_GROUPS] as List<DocumentReference>)

		groupReferences.mapNotNull { documentRef ->
			groupCollection.document(documentRef.id)
				.get()
				.await()
				.toObject(GroupFirestoreModel::class.java)?.toDomainModel(documentRef.id)
		}
	} catch (e: Exception) {
		throw e
	}

	override suspend fun getGroupByInviteCodes(inviteCodes: String): GroupModel = try {
		val groupSnapshot = inviteCodesCollection.document(inviteCodes)
			.get().await()
		val group = groupSnapshot[FirestoreConstants.FIELD_GROUP] as DocumentReference

		groupCollection.document(group.id)
			.get()
			.await()
			.toObject(GroupFirestoreModel::class.java)?.toDomainModel(group.id)
			?: throw Exception("Group not found")
	} catch (e: Exception) {
		throw e
	}

	override suspend fun joinGroup(userId: String, groupId: String) {
		groupCollection.document(groupId).update(
			FirestoreConstants.FIELD_MEMBERS,
			// 기존 유저 리스트에 덮어씌우지 않고 추가
			FieldValue.arrayUnion(userCollection.document(userId)),
		).await()

		userCollection.document(userId).update(
			FirestoreConstants.FIELD_GROUPS,
			FieldValue.arrayUnion(groupCollection.document(groupId)),
		).await()
	}

	override suspend fun issueInvitationCode(groupId: String): String = try {
		// 이미 해당 그룹에 대한 초대 코드가 존재하는지 확인
		val groupReference = groupCollection.document(groupId)
		val existingInviteCodeDocument = inviteCodesCollection
			.whereEqualTo(FirestoreConstants.FIELD_GROUP, groupReference)
			.get()
			.await()
			.documents
			.firstOrNull()

		// 이미 해당 그룹에 대한 초대 코드가 존재하면 해당 초대 코드 반환
		if (existingInviteCodeDocument != null) {
			existingInviteCodeDocument.id
		} else {
			val inviteCode = UUID.randomUUID().toString().replace("-", "")
			val timestamp = com.google.firebase.Timestamp.now()
			inviteCodesCollection.document(inviteCode).set(
				mapOf(
					FirestoreConstants.FIELD_GROUP to groupReference,
					FirestoreConstants.FIELD_CREATED_AT to timestamp,
				),
			).await()
			inviteCode
		}
	} catch (e: Exception) {
		throw e
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
}
