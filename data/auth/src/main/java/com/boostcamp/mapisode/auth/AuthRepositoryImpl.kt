package com.boostcamp.mapisode.auth

import androidx.credentials.exceptions.GetCredentialCancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val googleAuthDataSource: GoogleAuthDataSource,
	private val firebaseAuthDataSource: FirebaseAuthDataSource,
	private val userMapper: UserMapper,
) : AuthRepository {
	override suspend fun signInWithGoogle(): Flow<Result<User>> = callbackFlow {
		try {
			val googleCredential = googleAuthDataSource.getGoogleCredential()
			val firebaseAuthResult = firebaseAuthDataSource.signInWithGoogle(googleCredential)
			val user = userMapper.mapToUser(firebaseAuthResult)
			trySend(Result.success(user))
		} catch (e: GetCredentialCancellationException) {
			trySend(Result.failure(e))
		} catch (e: Exception) {
			trySend(Result.failure(e))
		}
		awaitClose { }
	}
}
