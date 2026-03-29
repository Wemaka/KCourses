package com.wemaka.kcourses.feature.favoritecourses.impl.state

import com.wemaka.kcourses.data.models.courses.Course

interface UiState {
    data class Error(
        val message: String? = null
    ) : UiState

    data object Loading : UiState
    data class Show(
        val courses: List<Course>
    ) : UiState
}