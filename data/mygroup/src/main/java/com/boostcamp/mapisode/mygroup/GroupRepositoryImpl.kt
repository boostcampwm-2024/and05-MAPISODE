package com.boostcamp.mapisode.mygroup

import com.boostcamp.mapisode.firebase.firestore.FirestoreConstants
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.model.GroupMemberModel
import com.boostcamp.mapisode.model.GroupModel
import com.boostcamp.mapisode.mygroup.model.GroupEpisodeFirestoreModel
import com.boostcamp.mapisode.mygroup.model.GroupFirestoreModel
import com.boostcamp.mapisode.mygroup.model.UserFirestoreModel
import com.boostcamp.mapisode.mygroup.model.toDomainModel
import com.boostcamp.mapisode.mygroup.model.toFirestoreModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Calendar
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
			?: throw Exception("그룹을 찾을 수 없습니다.")
	} catch (e: Exception) {
		throw e
	}

	override suspend fun joinGroup(userId: String, groupId: String) {
		try {
			database.runTransaction { transaction ->
				// 그룹 문서의 멤버 리스트에 사용자 추가
				val groupDocRef = database
					.collection(FirestoreConstants.COLLECTION_GROUP)
					.document(groupId)
				transaction.update(
					groupDocRef,
					FirestoreConstants.FIELD_MEMBERS,
					FieldValue.arrayUnion(
						database.collection(FirestoreConstants.COLLECTION_USER)
							.document(userId),
					),
				)

				// 사용자 문서의 그룹 리스트에 그룹 추가
				val userDocRef = database.collection(FirestoreConstants.COLLECTION_USER)
					.document(userId)
				transaction.update(
					userDocRef,
					FirestoreConstants.FIELD_GROUPS,
					FieldValue.arrayUnion(
						database.collection(FirestoreConstants.COLLECTION_GROUP)
							.document(groupId),
					),
				)
			}.await()
		} catch (e: Exception) {
			throw e
		}
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
			val timestamp = Timestamp(
				Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 7) }.time,
			)
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

	@Suppress("UNCHECKED_CAST")
	override suspend fun leaveGroup(userId: String, groupId: String) {
		try {
			database.runTransaction { transaction ->
				// group의 members 필드에서 사용자 제거
				val groupDocRef = database.collection(FirestoreConstants.COLLECTION_GROUP)
					.document(groupId)
				val groupSnapshot = transaction.get(groupDocRef)
				val members = groupSnapshot.get(
					FirestoreConstants.FIELD_MEMBERS,
				) as MutableList<DocumentReference>

				// user의 그룹 필드에서 그룹 제거
				val userDocRef = database.collection(FirestoreConstants.COLLECTION_USER)
					.document(userId)
				val userSnapshot = transaction.get(userDocRef)
				val groups = userSnapshot.get(
					FirestoreConstants.FIELD_GROUPS,
				) as MutableList<DocumentReference>

				members.remove(database.collection(FirestoreConstants.COLLECTION_USER).document(userId))
				transaction.update(groupDocRef, FirestoreConstants.FIELD_MEMBERS, members)

				groups.remove(
					database.collection(FirestoreConstants.COLLECTION_GROUP)
						.document(groupId),
				)
				transaction.update(userDocRef, FirestoreConstants.FIELD_GROUPS, groups)
			}.await()
		} catch (e: Exception) {
			throw e
		}
	}

	override suspend fun createGroup(groupModel: GroupModel) {
		try {
			database.runTransaction { transaction ->
				// group 컬렉션 생성
				val groupDocRef = database
					.collection(FirestoreConstants.COLLECTION_GROUP)
					.document(groupModel.id)
				transaction.set(groupDocRef, groupModel.toFirestoreModel(database))

				// user의 group 필드 업데이트
				val userDocRef = database
					.collection(FirestoreConstants.COLLECTION_USER)
					.document(groupModel.adminUser)
				transaction.update(
					userDocRef,
					FirestoreConstants.FIELD_GROUPS,
					FieldValue.arrayUnion(groupDocRef),
				)
			}.await()
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

	override suspend fun getUserInfoByUserId(userId: String): GroupMemberModel = try {
		val firestoreModel = userCollection.document(userId)
			.get()
			.await()
			.toObject(UserFirestoreModel::class.java)
			?: throw Exception("유저를 찾을 수 없습니다.")
		GroupMemberModel(
			id = userId,
			name = firestoreModel.name,
			email = firestoreModel.email,
			profileUrl = firestoreModel.profileUrl,
			joinedAt = firestoreModel.joinedAt.toDate(),
			groups = firestoreModel.groups.map { it.id },
		)
	} catch (e: Exception) {
		throw e
	}

	override suspend fun getEpisodesByGroupIdAndUserId(groupId: String, userId: String):
		List<EpisodeModel> = try {
		val groupDocRef = database
			.collection(FirestoreConstants.COLLECTION_GROUP).document(groupId)
		val userDocRef = database
			.collection(FirestoreConstants.COLLECTION_USER).document(userId)
		val episodeReferences = database.collection(FirestoreConstants.COLLECTION_EPISODE)
			.whereEqualTo(FirestoreConstants.FIELD_GROUP, groupDocRef)
			.whereEqualTo(FirestoreConstants.FIELD_CREATED_BY, userDocRef)
			.get()
			.await()
			.documents

		episodeReferences.mapNotNull { document ->
			document.toObject(GroupEpisodeFirestoreModel::class.java)?.toDomainModel(userId)
				?: throw Exception("에피소드를 찾을 수 없습니다.")
		}
	} catch (e: Exception) {
		throw e
	}
}
