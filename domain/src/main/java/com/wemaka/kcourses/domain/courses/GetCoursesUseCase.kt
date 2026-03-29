package com.wemaka.kcourses.domain.courses

import com.wemaka.kcourses.data.models.courses.Course
import com.wemaka.kcourses.data.remote.models.CoursesFilterData
import com.wemaka.kcourses.data.repositories.courses.CoursesRepository
import kotlinx.coroutines.flow.Flow

class GetCoursesUseCase(
    private val coursesRepository: CoursesRepository
) {
    operator fun invoke(filter: CoursesFilterData? = null): Flow<List<Course>> {
        return coursesRepository.getAll(filter = filter)
    }
}