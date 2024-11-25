package com.boostcamp.mapisode.user.model

data class UserFirestoreModel(
	val uid: String,
	val nickname: String,
	val email: String,
	val profileUri: String,
)
