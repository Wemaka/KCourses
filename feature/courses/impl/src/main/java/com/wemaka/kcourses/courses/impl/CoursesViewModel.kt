package com.wemaka.kcourses.courses.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wemaka.kcourses.courses.impl.state.SideEffect
import com.wemaka.kcourses.courses.impl.state.UiState
import com.wemaka.kcourses.data.remote.models.CoursesFilterData
import com.wemaka.kcourses.domain.courses.GetCoursesUseCase
import com.wemaka.kcourses.domain.courses.SetFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoursesViewModel(
    private val getCourses: GetCoursesUseCase,
    private val setFavorite: SetFavoriteUseCase
) : ViewModel() {
    private val _sideEffect = Channel<SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _filterState = MutableStateFlow(CoursesFilterData())

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<UiState> = _filterState
        .flatMapLatest { filter ->
            getCourses(filter)
                .map { courses ->
                    if (courses.isEmpty()) {
                        UiState.Error("empty")
                    } else {
                        UiState.Show(courses = courses)
                    }
                }
                .onStart { emit(UiState.Loading) }
                .catch { e -> emit(UiState.Error(message = e.message)) }
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UiState.Loading
        )

    fun toggleDateSortOrder() {
        _filterState.update {
            it.copy(publishAscending = !it.publishAscending)
        }
    }

    fun toggleFavoriteCourse(courseId: Int) {
        viewModelScope.launch {
            setFavorite(courseId)
        }
    }

    fun onSearchQuery(query: String) {
        _filterState.update {
            it.copy(searchQuery = query)
        }
    }
}
