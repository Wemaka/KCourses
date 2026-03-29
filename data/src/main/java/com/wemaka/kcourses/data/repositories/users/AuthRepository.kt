package com.wemaka.kcourses.data.repositories.users

import com.wemaka.kcourses.data.models.users.User

interface AuthRepository {
    suspend fun isUserLoggedIn(): Boolean

    suspend fun getCurrentUser(): User?

    suspend fun loginUser(user: User)

    suspend fun logoutUser()
}