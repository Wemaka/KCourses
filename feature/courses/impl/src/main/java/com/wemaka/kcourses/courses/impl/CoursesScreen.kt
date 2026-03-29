package com.wemaka.kcourses.courses.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wemaka.kcourses.common.compose.ObserveAsEvent
import com.wemaka.kcourses.courses.impl.components.CoursesContent
import com.wemaka.kcourses.courses.impl.state.SideEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoursesScreen(
    navigateToDetail: (Int) -> Unit
) {
    val viewModel: CoursesViewModel = koinViewModel()

    CoursesContent(
        state = viewModel.uiState.collectAsStateWithLifecycle().value,
        onSortByDate = viewModel::toggleDateSortOrder,
        onFavoriteClick = viewModel::toggleFavoriteCourse,
        onSearchQuery = viewModel::onSearchQuery
    )

    ObserveAsEvent(viewModel.sideEffect) { effect ->
        when (effect) {
            is SideEffect.NavigateToCourseDetail -> navigateToDetail(effect.courseId)
        }
    }
}