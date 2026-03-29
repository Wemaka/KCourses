package com.wemaka.kcourses.domain.courses

import com.wemaka.kcourses.data.repositories.courses.CoursesRepository

class SetFavoriteUseCase(
    private val coursesRepository: CoursesRepository
) {
    suspend operator fun invoke(courseId: Int) {
        coursesRepository.getCourse(courseId)?.let {
            coursesRepository.setFavoriteStatus(courseId, !it.hasLike)
        }
    }
}