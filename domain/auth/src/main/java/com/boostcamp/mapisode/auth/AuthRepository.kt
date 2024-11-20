package com.boostcamp.mapisode.auth

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
	suspend fun signInWithGoogle(): Flow<Result<User>>
}
