package com.boostcamp.mapisode.mygroup

import com.boostcamp.mapisode.model.GroupModel

interface GroupRepository {
	suspend fun getGroupById(groupId: String): GroupModel?
	suspend fun getAllGroups(): List<GroupModel>
	suspend fun createGroup(groupModel: GroupModel): String
	suspend fun updateGroup(groupId: String, groupModel: GroupModel)
	suspend fun deleteGroup(groupId: String)
}
