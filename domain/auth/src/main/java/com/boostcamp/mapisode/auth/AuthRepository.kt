package com.boostcamp.mapisode.auth

import com.boostcamp.mapisode.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
	suspend fun signInWithGoogle(): Flow<Result<User>>
}
