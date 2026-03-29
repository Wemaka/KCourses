package com.wemaka.kcourses.courses.impl.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wemaka.kcourses.common.extentions.formatLocalizedDate
import com.wemaka.kcourses.data.models.courses.Course
import com.wemaka.kcourses.uikit.R
import com.wemaka.kcourses.uikit.components.CourseCard
import com.wemaka.kcourses.uikit.space16
import com.wemaka.kcourses.uikit.space8

@Composable
internal fun CourseList(
    items: List<Course>,
    listState: LazyListState,
    onSortByDate: () -> Unit,
    onFavoriteClick: (Int) -> Unit
) {
    val locale = LocalConfiguration.current.locales[0]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space16),
        state = listState,
        contentPadding = PaddingValues(
            bottom = space16
        )
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = space8),
                horizontalArrangement = Arrangement.End
            ) {
                Row(
                    modifier = Modifier
                        .clickable { onSortByDate() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.sort_by_date),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_down_up),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        items(
            items = items,
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