package com.boostcamp.mapisode.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.boostcamp.mapisode.login.BuildConfig
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class GoogleAuthDataSourceImpl @Inject constructor(
	@ApplicationContext private val context: Context,
) : GoogleAuthDataSource {
	override suspend fun getGoogleCredential(): GoogleIdTokenCredential {
		val credentialManager = CredentialManager.create(context)
		val hashedNonce = generateNonce()

		val googleIdOption =
			GetSignInWithGoogleOption.Builder(BuildConfig.GOOGLE_WEB_CLIENT_ID)
				.setNonce(hashedNonce)
				.build()

		val request = GetCredentialRequest.Builder()
			.addCredentialOption(googleIdOption)
			.build()

		val result = credentialManager.getCredential(context, request)

		return when (val credential = result.credential) {
			is CustomCredential -> {
				if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
					GoogleIdTokenCredential.createFrom(credential.data)
				} else {
					throw RuntimeException("Invalid credential type")
				}
			}

			else -> throw RuntimeException("Invalid credential")
		}
	}

	private fun generateNonce(): String {
		val ranNonce = UUID.randomUUID().toString()
		val bytes = ranNonce.toByteArray()
		val md = MessageDigest.getInstance("SHA-256")
		val digest = md.digest(bytes)
		return digest.fold("") { str, it -> str + "%02x".format(it) }
	}
}
