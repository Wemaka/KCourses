package com.wemaka.kcourses.data.remote.models

data class CoursesFilterData(
    val searchQuery: String = "",
    val publishAscending: Boolean = true,
    val hasLike: Boolean? = null
)