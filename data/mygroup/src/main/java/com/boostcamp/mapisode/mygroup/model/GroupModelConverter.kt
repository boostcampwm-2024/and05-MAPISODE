package com.boostcamp.mapisode.mygroup.model

import com.boostcamp.mapisode.model.GroupModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

internal fun GroupModel.toFirestoreModel(database: FirebaseFirestore) = GroupFirestoreModel(
	adminUser = database.collection("adminUser").document(adminUser),
	createdAt = Timestamp(createdAt),
	description = description,
	imageUrl = imageUrl,
	name = name,
	members = members.map { database.collection("members").document(it) },
)
