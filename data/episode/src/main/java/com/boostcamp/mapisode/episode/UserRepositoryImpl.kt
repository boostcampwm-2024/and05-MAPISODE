package com.boostcamp.mapisode.episode

import com.boostcamp.mapisode.episode.model.toUserFirestoreModel
import com.boostcamp.mapisode.firebase.firestore.FirestoreConstants
import com.boostcamp.mapisode.model.UserModel
import com.boostcamp.mapisode.user.UserRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val database: FirebaseFirestore) :
	UserRepository {
	private val userCollection = database.collection(FirestoreConstants.COLLECTION_USER)

	override suspend fun createUser(userModel: UserModel) {
		val userFirestoreModel = userModel.toUserFirestoreModel(database)
		val userData = mapOf(
			"nickname" to userFirestoreModel.nickname,
			"email" to userFirestoreModel.email,
			"profileUri" to userFirestoreModel.profileUri,
			"joinedAt" to FieldValue.serverTimestamp(),
		)
		val userDocument = userCollection.document(userFirestoreModel.uid)

		userDocument
			.get()
			.addOnSuccessListener { document ->
				if (document.exists()) {
					Timber.tag("createUser").d("User already exists")
					return@addOnSuccessListener
				} else {
					userDocument
						.set(userData)
						.addOnSuccessListener {
							Timber.tag("createUser").d("User created successfully")
						}
						.addOnFailureListener { e -> Timber.tag("createUser").e(e) }
				}
			}
	}
}
