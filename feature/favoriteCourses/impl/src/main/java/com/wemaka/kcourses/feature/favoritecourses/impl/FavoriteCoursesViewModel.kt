package com.wemaka.kcourses.feature.favoritecourses.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wemaka.kcourses.data.remote.models.CoursesFilterData
import com.wemaka.kcourses.domain.courses.GetCoursesUseCase
import com.wemaka.kcourses.domain.courses.SetFavoriteUseCase
import com.wemaka.kcourses.feature.favoritecourses.impl.state.SideEffect
import com.wemaka.kcourses.feature.favoritecourses.impl.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteCoursesViewModel(
    private val getCourses: GetCoursesUseCase,
    private val setFavorite: SetFavoriteUseCase
) : ViewModel() {
    private val _sideEffect = Channel<SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    val uiState: StateFlow<UiState> = getCourses(CoursesFilterData(hasLike = true))
        .map { courses ->
            if (courses.isEmpty()) {
                UiState.Error("empty")
            } else {
                UiState.Show(courses = courses)
            }
        }
        .onStart { emit(UiState.Loading) }
        .catch { e -> emit(UiState.Error(message = e.message)) }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UiState.Loading
        )

    fun toggleFavoriteCourse(courseId: Int) {
        viewModelScope.launch {
            setFavorite(courseId)
        }
    }
}