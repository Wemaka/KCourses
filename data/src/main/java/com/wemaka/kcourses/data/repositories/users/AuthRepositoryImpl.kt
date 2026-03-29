package com.wemaka.kcourses.data.repositories.users

import com.wemaka.kcourses.data.db.UsersDao
import com.wemaka.kcourses.data.db.entity.UserEntity
import com.wemaka.kcourses.data.db.entity.toExternal
import com.wemaka.kcourses.data.models.users.User
import com.wemaka.kcourses.data.models.users.toLocal

class AuthRepositoryImpl(
    private val usersDao: UsersDao
) : AuthRepository {
    override suspend fun isUserLoggedIn(): Boolean {
        return usersDao.getActiveUsersCount() > 0
    }

    override suspend fun getCurrentUser(): User? {
        return usersDao.getActiveUser()?.toExternal()
    }

    override suspend fun loginUser(user: User) {
        usersDao.clearActiveUsers()
        usersDao.upsertUser(
            user.toLocal().copy(isLoggedIn = true)
        )
    }

    override suspend fun logoutUser() {
        usersDao.clearActiveUsers()
    }
}