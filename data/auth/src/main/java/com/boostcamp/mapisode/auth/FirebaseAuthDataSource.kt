package com.boostcamp.mapisode.auth

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult

interface FirebaseAuthDataSource {
	suspend fun signInWithGoogle(credential: GoogleIdTokenCredential): AuthResult
}
