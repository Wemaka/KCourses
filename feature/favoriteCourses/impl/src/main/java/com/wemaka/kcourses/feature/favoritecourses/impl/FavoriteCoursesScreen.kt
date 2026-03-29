package com.wemaka.kcourses.feature.favoritecourses.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wemaka.kcourses.common.compose.ObserveAsEvent
import com.wemaka.kcourses.feature.favoritecourses.impl.components.FavoriteCoursesContent
import com.wemaka.kcourses.feature.favoritecourses.impl.state.SideEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteCoursesScreen(
    navigateToDetail: (Int) -> Unit
) {
    val viewModel: FavoriteCoursesViewModel = koinViewModel()

    FavoriteCoursesContent(
        state = viewModel.uiState.collectAsStateWithLifecycle().value,
        onFavoriteClick = viewModel::toggleFavoriteCourse
    )

    ObserveAsEvent(viewModel.sideEffect) { effect ->
        when (effect) {
            is SideEffect.NavigateToCourseDetail -> navigateToDetail(effect.courseId)
        }
    }
}