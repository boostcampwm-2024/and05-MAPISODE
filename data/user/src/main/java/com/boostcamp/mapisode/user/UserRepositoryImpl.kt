package com.boostcamp.mapisode.user

import com.boostcamp.mapisode.firebase.firestore.FirestoreConstants
import com.boostcamp.mapisode.model.UserModel
import com.boostcamp.mapisode.user.model.toUserFirestoreModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(database: FirebaseFirestore) :
	UserRepository {
	private val userCollection = database.collection(FirestoreConstants.COLLECTION_USER)

	override suspend fun createUser(userModel: UserModel) {
		val userFirestoreModel = userModel.toUserFirestoreModel()

		val userDocument = userCollection.document(userFirestoreModel.uid)

		try {
			val document = userDocument.get().await()
			if (document.exists()) {
				throw Exception("User already exists")
			}
			userDocument.set(userFirestoreModel).await()
		} catch (e: Exception) {
			throw Exception("Failed to create user", e)
		}
	}
}
