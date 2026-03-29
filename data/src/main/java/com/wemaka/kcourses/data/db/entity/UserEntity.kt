package com.wemaka.kcourses.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wemaka.kcourses.data.models.users.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val isLoggedIn: Boolean = false
)

fun UserEntity.toExternal(): User = User(
    id = id,
    email = email
)
