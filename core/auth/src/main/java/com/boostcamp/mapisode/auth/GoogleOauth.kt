package com.boostcamp.mapisode.auth

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.boostcamp.mapisode.model.AuthData
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID

class GoogleOauth(private val context: Context) {

	suspend fun googleSignIn(): Flow<LoginState> {
		val firebaseAuth = FirebaseAuth.getInstance()
		return callbackFlow {
			try {
				val credentialManager = initializeCredentialManager()
				val googleIdOption = createGoogleSignInOption()
				val request = createCredentialRequest(googleIdOption)

				val resultCredential = credentialManager.getCredential(context, request).credential

				val validatedCredential: GoogleIdTokenCredential =
					validateCredential(resultCredential)
				val authResult: AuthResult =
					authenticateWithFirebase(firebaseAuth, validatedCredential)

				val firebaseUID = authResult.user?.uid ?: throw Exception("Firebase UID가 없습니다.")
				val googleIdToken = validatedCredential.idToken
				val googleName = validatedCredential.run { "${familyName ?: ""}${givenName ?: ""}" }
				val googleEmail = validatedCredential.id

				trySend(
					LoginState.Success(
						googleIdToken,
						AuthData(
							uid = firebaseUID,
							displayName = googleName,
							idToken = googleIdToken,
							email = googleEmail,
						),
					),
				)
			} catch (e: GetCredentialCancellationException) {
				trySend(
					LoginState.Error(e.message ?: "Google Sign 실패"),
				)
			} catch (e: Exception) {
				trySend(
					LoginState.Error(e.message ?: "Google Sign 실패"),
				)
			}
			awaitClose { }
		}
	}

	private fun initializeCredentialManager(): CredentialManager = CredentialManager.create(context)

	private fun createGoogleSignInOption(): GetSignInWithGoogleOption = GetSignInWithGoogleOption
		.Builder(BuildConfig.GOOGLE_WEB_CLIENT_ID)
		.setNonce(generateNonce())
		.build()

	private fun createCredentialRequest(googleIdOption: GetSignInWithGoogleOption):
		GetCredentialRequest = GetCredentialRequest.Builder()
		.addCredentialOption(googleIdOption)
		.build()

	private fun validateCredential(credential: Credential): GoogleIdTokenCredential {
		if (credential is CustomCredential &&
			credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
		) {
			return GoogleIdTokenCredential.createFrom(credential.data)
		} else {
			throw RuntimeException("유효하지 않는 credential type")
		}
	}

	private suspend fun authenticateWithFirebase(
		firebaseAuth: FirebaseAuth,
		googleIdTokenCredential: GoogleIdTokenCredential,
	): AuthResult {
		val authCredential =
			GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
		return firebaseAuth.signInWithCredential(authCredential).await()
	}

	private fun generateNonce(): String {
		val ranNonce = UUID.randomUUID().toString()
		val bytes = ranNonce.toByteArray()
		val md = MessageDigest.getInstance("SHA-256")
		val digest = md.digest(bytes)
		val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }
		return hashedNonce
	}
}
