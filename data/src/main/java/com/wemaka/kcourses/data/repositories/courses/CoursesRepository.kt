package com.wemaka.kcourses.data.repositories.courses

import com.wemaka.kcourses.data.models.courses.Course
import com.wemaka.kcourses.data.remote.models.CoursesFilterData
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun getCourse(courseId: Int): Course?
    fun getAll(filter: CoursesFilterData? = null): Flow<List<Course>>
    suspend fun setFavoriteStatus(courseId: Int, hasLike: Boolean)
}
