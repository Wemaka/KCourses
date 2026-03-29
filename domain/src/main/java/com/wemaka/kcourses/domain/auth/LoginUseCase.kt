package com.wemaka.kcourses.domain.auth

import com.wemaka.kcourses.data.models.users.User
import com.wemaka.kcourses.data.repositories.users.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(user: User) {
        authRepository.loginUser(user)
    }
}