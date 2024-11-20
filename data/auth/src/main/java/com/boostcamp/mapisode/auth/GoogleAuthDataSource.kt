package com.boostcamp.mapisode.auth

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

interface GoogleAuthDataSource {
	suspend fun getGoogleCredential(): GoogleIdTokenCredential
}
