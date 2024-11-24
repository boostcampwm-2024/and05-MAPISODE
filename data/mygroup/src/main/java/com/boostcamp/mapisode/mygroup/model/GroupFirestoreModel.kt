package com.boostcamp.mapisode.mygroup.model

import com.boostcamp.mapisode.model.GroupModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class GroupFirestoreModel(
	val id: String = "",
	val adminUser: DocumentReference? = null,
	val createdAt: Timestamp = Timestamp.now(),
	val description: String = "",
	val imageUrl: String = "",
	val name: String = "",
	val members: List<DocumentReference> = listOf(),
)

fun GroupFirestoreModel.toDomainModel(groupId: String) = GroupModel(
	id = groupId,
	adminUser = adminUser?.id ?: "",
	createdAt = createdAt.toDate(),
	description = description,
	imageUrl = imageUrl,
	name = name,
	members = members.map { it.id },
)
