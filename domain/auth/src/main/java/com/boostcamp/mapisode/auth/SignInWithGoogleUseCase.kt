package com.boostcamp.mapisode.auth

import com.boostcamp.mapisode.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(private val authRepository: AuthRepository) {
	suspend operator fun invoke(): Flow<Result<User>> = authRepository.signInWithGoogle()
}
