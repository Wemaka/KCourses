package com.wemaka.kcourses.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.wemaka.kcourses.data.db.entity.UserEntity

@Dao
interface UsersDao {
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1")
    suspend fun getActiveUser(): UserEntity?

    @Query("SELECT COUNT(*) FROM users WHERE isLoggedIn = 1")
    suspend fun getActiveUsersCount(): Int

    @Upsert
    suspend fun upsertUser(user: UserEntity)

    @Query("UPDATE users SET isLoggedIn = 0 WHERE isLoggedIn = 1")
    suspend fun clearActiveUsers()
}