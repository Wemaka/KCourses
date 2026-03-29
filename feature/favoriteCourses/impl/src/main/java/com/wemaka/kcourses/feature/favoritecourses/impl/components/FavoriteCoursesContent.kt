package com.wemaka.kcourses.feature.favoritecourses.impl.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wemaka.kcourses.common.extentions.formatLocalizedDate
import com.wemaka.kcourses.data.models.courses.Course
import com.wemaka.kcourses.feature.favoritecourses.impl.state.UiState
import com.wemaka.kcourses.uikit.R
import com.wemaka.kcourses.uikit.components.CourseCard
import com.wemaka.kcourses.uikit.components.ErrorBox
import com.wemaka.kcourses.uikit.space16
import com.wemaka.kcourses.uikit.theme.KCoursesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FavoriteCoursesContent(
    state: UiState,
    onFavoriteClick: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val refreshState = rememberPullToRefreshState()
    val locale = LocalConfiguration.current.locales[0]

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets(0),
            topBar = {
                TopAppBar(
                    modifier = Modifier.padding(top = space16),
                    title = {
                        Text(
                            text = stringResource(R.string.nav_favorites),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    },
                    windowInsets = WindowInsets.statusBars,
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = space16)
            ) {
                when (state) {
                    is UiState.Error -> {
                        ErrorBox(
                            title = stringResource(R.string.state_empty),
                            description = stringResource(R.string.favorites_empty_description),
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.geometric),
                                    contentDescription = null,
                                    modifier = Modifier.size(140.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    }

                    UiState.Loading -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(48.dp))
                            }
                        }
                    }

                    is UiState.Show -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(space16),
                            state = listState,
                            contentPadding = PaddingValues(
                                bottom = space16
                            )
                        ) {
                            items(
                                items = state.courses,
                                key = { it.id }
                            ) { course ->
                                CourseCard(
                                    imageUrl = "",
                                    rate = course.rate,
                                    date = course.startDate.formatLocalizedDate(locale),
                                    title = course.title,
                                    description = course.text,
                                    price = course.price,
                                    isFavorite = course.hasLike,
                                    onFavoriteClick = { onFavoriteClick(course.id) },
                                    onCardClick = {}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    KCoursesTheme() {
        FavoriteCoursesContent(
            state = UiState.Show(
                courses = List(5) {
                    Course(
                        id = it,
                        title = "Java-разработчик с нуля",
                        text = "Освойте backend-разработку \nи программирование на Java, фреймворки Spring и Maven, " +
                                "работу с базами данных и",
                        price = "999",
                        rate = "4.9",
                        startDate = "22 Мая 2024",
                        hasLike = false,
                        publishDate = "22 Мая 2024"
                    )
                }
            ),
            onFavoriteClick = {},
        )
    }
}