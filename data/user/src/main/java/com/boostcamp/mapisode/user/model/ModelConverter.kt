package com.boostcamp.mapisode.user.model

import com.boostcamp.mapisode.model.UserModel

internal fun UserModel.toUserFirestoreModel(): UserFirestoreModel =
	UserFirestoreModel(
		uid = uid,
		nickname = name,
		email = email,
		profileUri = profileUrl,
	)
