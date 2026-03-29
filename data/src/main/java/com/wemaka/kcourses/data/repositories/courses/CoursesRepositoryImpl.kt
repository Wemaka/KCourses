package com.wemaka.kcourses.data.repositories.courses

import com.wemaka.kcourses.data.db.CoursesDao
import com.wemaka.kcourses.data.db.entity.toExternal
import com.wemaka.kcourses.data.models.courses.Course
import com.wemaka.kcourses.data.remote.CoursesApi
import com.wemaka.kcourses.data.remote.models.CoursesFilterData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class CoursesRepositoryImpl(
    val api: CoursesApi,
    val local: CoursesDao
) : CoursesRepository {
    override suspend fun getCourse(courseId: Int): Course? {
        return local.getCourseById(courseId)?.toExternal()
    }

    override fun getAll(filter: CoursesFilterData?): Flow<List<Course>> {
        return local.getAll().map { courseEntities ->
            var courses = courseEntities.toExternal()

            if (filter == null) return@map courses

            if (filter.searchQuery.isNotEmpty()) {
                courses = courses.filter {
                    it.title.contains(filter.searchQuery, ignoreCase = true)
                }
            }

            val filtered = if (filter.hasLike != null) {
                courses.filter { it.hasLike == filter.hasLike }
            } else {
                courses
            }

            val isAscending = filter.publishAscending
            if (isAscending) {
                filtered.sortedBy { LocalDate.parse(it.publishDate) }
            } else {
                filtered.sortedByDescending { LocalDate.parse(it.publishDate) }
            }
        }
    }

    override suspend fun setFavoriteStatus(courseId: Int, hasLike: Boolean) {
        local.setFavoriteStatus(courseId, hasLike)
    }
}