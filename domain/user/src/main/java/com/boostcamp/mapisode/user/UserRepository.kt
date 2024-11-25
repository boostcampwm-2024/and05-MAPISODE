package com.boostcamp.mapisode.user

import com.boostcamp.mapisode.model.UserModel

interface UserRepository {
	suspend fun createUser(userModel: UserModel)
}
