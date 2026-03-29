package com.wemaka.kcourses.courses.impl.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wemaka.kcourses.common.extentions.formatLocalizedDate
import com.wemaka.kcourses.courses.impl.state.UiState
import com.wemaka.kcourses.data.models.courses.Course
import com.wemaka.kcourses.uikit.R
import com.wemaka.kcourses.uikit.components.CourseCard
import com.wemaka.kcourses.uikit.components.ErrorBox
import com.wemaka.kcourses.uikit.components.TopSearchBar
import com.wemaka.kcourses.uikit.space16
import com.wemaka.kcourses.uikit.space8
import com.wemaka.kcourses.uikit.theme.KCoursesTheme
import com.wemaka.kcourses.uikit.topBarHeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CoursesContent(
    state: UiState,
    onSortByDate: () -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onSearchQuery: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val listState = rememberLazyListState()
    val refreshState = rememberPullToRefreshState()

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
                    title = {},
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
                            description = stringResource(R.string.search_no_results)
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
                        PullToRefreshBox(
                            modifier = Modifier.fillMaxSize(),
                            state = refreshState,
                            isRefreshing = false,
                            onRefresh = { }
                        ) {
                            CourseList(
                                items = state.courses,
                                listState = listState,
                                onSortByDate = onSortByDate,
                                onFavoriteClick = onFavoriteClick
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = space16)
                .clipToBounds()
                .padding(bottom = space8)
                .graphicsLayer {
                    translationY = scrollBehavior.state.heightOffset
                }
        ) {
            TopBar(
                onSearch = onSearchQuery
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember("") {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = space16),
        horizontalArrangement = Arrangement.spacedBy(space8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TopSearchBar(
            modifier = modifier
                .weight(1f),
            expanded = false,
            onExpandedChange = {},
            query = query,
            onQueryChange = {
                query = it
            },
            onSearch = onSearch,
            leadingIcon = {
                IconButton(
                    onClick = {
                        onSearch(query)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null
                    )
                }
            }
        ) {}

        IconButton(
            modifier = Modifier
                .height(topBarHeight)
                .aspectRatio(1f),
            onClick = {
//                Filter
            },
            shape = CircleShape,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filter),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    KCoursesTheme() {
        CoursesContent(
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
            onSortByDate = {},
            onFavoriteClick = {},
            onSearchQuery = {}
        )
    }
}
