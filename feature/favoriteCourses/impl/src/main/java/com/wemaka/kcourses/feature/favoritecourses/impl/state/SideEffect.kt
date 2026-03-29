package com.wemaka.kcourses.feature.favoritecourses.impl.state

interface SideEffect {
    data class NavigateToCourseDetail(
        val courseId: Int
    ) : SideEffect
}