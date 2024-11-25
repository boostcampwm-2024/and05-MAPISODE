package com.boostcamp.mapisode.user.model

import com.boostcamp.mapisode.model.UserModel
import com.google.firebase.firestore.FirebaseFirestore

internal fun UserModel.toUserFirestoreModel(database: FirebaseFirestore): UserFirestoreModel =
	UserFirestoreModel(
		uid = uid,
		nickname = nickname,
		email = email,
		profileUri = profileUri,
	)
