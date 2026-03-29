package com.wemaka.kcourses.ui.navigation

import com.wemaka.kcourses.navigation.BaseRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginGraph : BaseRoute

@Serializable
data object CoursesGraph : BaseRoute

@Serializable
data object FavoriteCoursesGraph : BaseRoute

@Serializable
data object AccountGraph : BaseRoute
