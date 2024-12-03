package com.boostcamp.mapisode.user

import androidx.core.net.toUri
import com.boostcamp.mapisode.firebase.firestore.FirestoreConstants
import com.boostcamp.mapisode.firebase.firestore.StorageConstants.PATH_IMAGES
import com.boostcamp.mapisode.model.UserModel
import com.boostcamp.mapisode.user.model.toUserFirestoreModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
	private val database: FirebaseFirestore,
	private val storage: FirebaseStorage,
) : UserRepository {
	private val userCollection = database.collection(FirestoreConstants.COLLECTION_USER)

	override suspend fun createUser(userModel: UserModel) {
		val userFirestoreModel = userModel.toUserFirestoreModel()

		val userDocument = userCollection.document(userModel.uid)

		try {
			val document = userDocument.get().await()

			if (document.exists()) throw Exception("User already exists")

			userDocument.set(userFirestoreModel).await()
		} catch (e: Exception) {
			throw Exception("Failed to create user", e)
		}
	}

	override suspend fun getUserInfo(uid: String): UserModel {
		val userDocument = userCollection.document(uid)

		try {
			val user = userDocument.get().await()
			return UserModel(
				uid = user.id,
				name = user.getString("name") ?: "",
				email = user.getString("email") ?: "",
				profileUrl = user.getString("profileUrl") ?: "",
				joinedAt = user.getDate("joinedAt") ?: Date.from(java.time.Instant.now()),
				groups = emptyList(), // 우선 빈 리스트를 넣도록 했습니다. 혹시나 사용하실 분은 수정해주세요.
			)
		} catch (e: Exception) {
			throw Exception("Failed to get user", e)
		}
	}

	override suspend fun isUserExist(uid: String): Boolean {
		val userDocument = userCollection.document(uid)

		try {
			val document = userDocument.get().await()
			return document.exists()
		} catch (e: Exception) {
			throw Exception("Failed to get user", e)
		}
	}

	override suspend fun updateUserNameAndProfileUrl(
		uid: String,
		userName: String,
		profileUrl: String,
	) {
		val userDocument = userCollection.document(uid)

		try {
			userDocument.update(
				mapOf(
					"name" to userName,
					"profileUrl" to profileUrl,
				),
			).await()
		} catch (e: Exception) {
			throw Exception("Failed to update user", e)
		}
	}

	override suspend fun uploadProfileImageToStorage(
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
