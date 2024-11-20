package com.boostcamp.mapisode.auth

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSourceImpl @Inject constructor(
	private val firebaseAuth: FirebaseAuth,
) : FirebaseAuthDataSource {
	override suspend fun signInWithGoogle(credential: GoogleIdTokenCredential): AuthResult {
		val authCredential = GoogleAuthProvider.getCredential(credential.idToken, null)
		return firebaseAuth.signInWithCredential(authCredential).await()
	}
}
