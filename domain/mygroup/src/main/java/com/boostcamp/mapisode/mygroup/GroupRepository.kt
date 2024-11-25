package com.boostcamp.mapisode.mygroup

import com.boostcamp.mapisode.model.GroupModel

interface GroupRepository {
	suspend fun getGroupsByUserId(userId: String): List<GroupModel>
	suspend fun createGroup(groupModel: GroupModel): String
	suspend fun updateGroup(groupId: String, groupModel: GroupModel)
	suspend fun deleteGroup(groupId: String)
}
