package com.boostcamp.mapisode.firebase.firestore

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FirestoreReference {
	companion object {
		fun database() = Firebase.firestore
	}
}
