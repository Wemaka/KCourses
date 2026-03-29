package com.wemaka.kcourses.courses.impl.state

import com.wemaka.kcourses.data.models.courses.Course

interface UiState {
    data class Error(
        val message: String? = null,
        val isRefreshing: Boolean = false,
    ) : UiState

    data object Loading : UiState
    data class Show(
        val courses: List<Course>,
        val isRefreshing: Boolean = false,
    ) : UiState
}