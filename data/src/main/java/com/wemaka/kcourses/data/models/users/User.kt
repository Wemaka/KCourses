package com.wemaka.kcourses.data.models.users

import com.wemaka.kcourses.data.db.entity.UserEntity

data class User(
    val id: String,
    val email: String
)

fun User.toLocal(): UserEntity = UserEntity(
    id = id,
    email = email
)
