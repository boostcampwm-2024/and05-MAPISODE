package com.boostcamp.mapisode.user

import com.boostcamp.mapisode.firebase.firestore.FirestoreConstants
import com.boostcamp.mapisode.model.UserModel
import com.boostcamp.mapisode.user.model.toUserFirestoreModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val database: FirebaseFirestore) :
	UserRepository {
	private val userCollection = database.collection(FirestoreConstants.COLLECTION_USER)

	override suspend fun createUser(userModel: UserModel) {
		val userFirestoreModel = userModel.toUserFirestoreModel(database)
		Timber.d("userFirestoreModel: $userFirestoreModel")
		val userData = mapOf(
			"nickname" to userFirestoreModel.nickname,
			"email" to userFirestoreModel.email,
			"profileUri" to userFirestoreModel.profileUri,
			"joinedAt" to FieldValue.serverTimestamp(),
		)
		val userDocument = userCollection.document(userFirestoreModel.uid)
		Timber.d("userDocument: $userDocument")

		try {
			val document = userDocument.get().await()
			if (document.exists()) {
				throw Exception("User already exists")
			}
			userDocument.set(userData).await()
		} catch (e: Exception) {
			throw Exception("Failed to create user", e)
		}
	}
}
