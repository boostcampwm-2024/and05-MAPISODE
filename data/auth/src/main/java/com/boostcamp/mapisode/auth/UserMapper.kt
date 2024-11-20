package com.boostcamp.mapisode.auth

import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class UserMapper @Inject constructor() {
	fun mapToUser(authResult: AuthResult): User {
		return User(
			id = authResult.user?.uid ?: throw RuntimeException("User ID is null"),
			displayName = authResult.user?.displayName,
		)
	}
}
