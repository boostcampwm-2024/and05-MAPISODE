package com.boostcamp.mapisode.mygroup

import com.boostcamp.mapisode.model.GroupModel

interface GroupRepository {
	suspend fun getGroupsByUserId(userId: String): List<GroupModel>
	suspend fun getGroupById(inviteCodes: String): GroupModel
	suspend fun joinGroup(userId: String, groupId: String)
	suspend fun createGroup(groupModel: GroupModel): String
	suspend fun updateGroup(groupId: String, groupModel: GroupModel)
	suspend fun deleteGroup(groupId: String)
}
