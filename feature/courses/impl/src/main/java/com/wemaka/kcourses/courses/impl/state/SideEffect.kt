package com.wemaka.kcourses.courses.impl.state

sealed interface SideEffect {
    data class NavigateToCourseDetail(
        val courseId: Int
    ) : SideEffect
}