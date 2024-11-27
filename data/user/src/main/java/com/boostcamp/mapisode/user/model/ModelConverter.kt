package com.boostcamp.mapisode.user.model

import com.boostcamp.mapisode.model.UserModel

internal fun UserModel.toUserFirestoreModel(): UserFirestoreModel =
	UserFirestoreModel(
		name = name,
		email = email,
		profileUrl = profileUrl,
		joinedAt = joinedAt,
	)
