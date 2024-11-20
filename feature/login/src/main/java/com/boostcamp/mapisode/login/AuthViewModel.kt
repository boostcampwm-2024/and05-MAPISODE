package com.boostcamp.mapisode.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

	fun handleGoogleSignIn(context: Context) {
		viewModelScope.launch {
			googleSignIn(context).collect { result ->
				result.fold(
					onSuccess = { authResult ->
						val user = authResult.user
						if (user!=null) {
							Timber.tag("AuthViewModel").d("Sign-in successful! User: %s", user.displayName)
						} else {
							Timber.tag("AuthViewModel").d("Sign-in succeeded but user is null.")
						}
					},
					onFailure = { e ->
						when (e) {
							is GetCredentialCancellationException -> {
								Timber.tag("AuthViewModel").e("Sign-in canceled by user: %s", e.message)
							}

							else -> {
								Timber.tag("AuthViewModel").e("Sign-in failed: %s", e.localizedMessage)
							}
						}
					},
				)
			}
		}
	}

	private suspend fun googleSignIn(context: Context): Flow<Result<AuthResult>> {
		val firebaseAuth = FirebaseAuth.getInstance()
		return callbackFlow {
			try {
				// Credential Manager 초기화
				val credentialManager: CredentialManager = CredentialManager.create(context)

				// nonce 생성, 클라이언트에서 생성하여 서버에서 검증
				val ranNonce: String = UUID.randomUUID().toString()
				val bytes: ByteArray = ranNonce.toByteArray()
				val md: MessageDigest = MessageDigest.getInstance("SHA-256")
				val digest: ByteArray = md.digest(bytes)
				val hashedNonce: String = digest.fold("") { str, it -> str + "%02x".format(it) }
				Timber.tag("Hashed nonce: $hashedNonce")

				val googleIdOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(BuildConfig.GOOGLE_WEB_CLIENT_ID)
					.setNonce(hashedNonce)
					.build()
				Timber.tag("AuthViewModel").e(BuildConfig.GOOGLE_WEB_CLIENT_ID)

				// credentials 요청
				val request: GetCredentialRequest = GetCredentialRequest.Builder()
					.addCredentialOption(googleIdOption)
					.build()
				Timber.tag("AuthViewModel").e("Request: " + request)

				// credential 결과 받기
				val result = credentialManager.getCredential(context, request)
				Timber.tag("AuthViewModel").e("Result: " + result)

				// credential이 GoogleIdTokenCredential인지 확인
				val credential = result.credential
				Timber.tag("AuthViewModel").e("Credential: " + credential)

				// GoogleIdTokenCredential로 변환
				if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
					val googleIdTokenCredential =
						GoogleIdTokenCredential.createFrom(credential.data)
					val authCredential =
						GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
					val authResult = firebaseAuth.signInWithCredential(authCredential).await()
					trySend(Result.success(authResult))
				} else {
					throw RuntimeException("Received an invalid credential type")
				}
			} catch (e: GetCredentialCancellationException) {
				trySend(Result.failure(Exception(e)))

			} catch (e: Exception) {
				trySend(Result.failure(e))
			}
			awaitClose { }
		}
	}
}
