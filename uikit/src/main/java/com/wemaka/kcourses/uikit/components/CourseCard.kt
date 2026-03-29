package com.wemaka.kcourses.uikit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.wemaka.kcourses.uikit.MediumBoxShape
import com.wemaka.kcourses.uikit.R
import com.wemaka.kcourses.uikit.space12
import com.wemaka.kcourses.uikit.space16
import com.wemaka.kcourses.uikit.space4
import com.wemaka.kcourses.uikit.space8
import com.wemaka.kcourses.uikit.theme.KCoursesTheme

@Composable
fun CourseCard(
    imageUrl: String,
    rate: String,
    date: String,
    title: String,
    description: String,
    price: String,
    modifier: Modifier = Modifier,
    cardShape: Shape = MediumBoxShape,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1.4f)
            .fillMaxWidth(),
        shape = cardShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MediumBoxShape),
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    alignment = BiasAlignment(horizontalBias = 0.0f, verticalBias = -0.7f),
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp)
                        )
                    },
                    error = {
                        ImageError()
                    }
                )

                BlurBox(
                    modifier = modifier
                        .align(Alignment.TopEnd)
                        .padding(space8)
                        .size(28.dp)
                        .clickable { onFavoriteClick() },
                    contentAlignment = Alignment.Center,
                    shape = CircleShape
                ) {
                    if (isFavorite) {
                        Icon(
                            painter = painterResource(R.drawable.ic_mark_solid),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_mark),
                            contentDescription = null
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(space8),
                    horizontalArrangement = Arrangement.spacedBy(space4)
                ) {
                    BlurBox(
                        modifier = modifier
                            .wrapContentSize(),
                        contentAlignment = Alignment.Center,
                        shape = CircleShape
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = space4, horizontal = space8),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(space4)
                        ) {
                            val textStyle = MaterialTheme.typography.labelSmall
                            val iconSize = with(LocalDensity.current) {
                                textStyle.fontSize.toDp()
                            }

                            Icon(
                                modifier = Modifier
                                    .size(iconSize),
                                painter = painterResource(R.drawable.ic_star),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = rate,
                                style = textStyle
                            )
                        }
                    }

                    BlurBox(
                        modifier = modifier
                            .wrapContentSize(),
                        contentAlignment = Alignment.Center,
                        shape = CircleShape
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = space4, horizontal = space8),
                            text = date,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(space16),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(space12)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$price ₽",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Row(
                        modifier = Modifier
                            .clickable { onCardClick() },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(space4)
                    ) {
                        Text(
                            text = stringResource(R.string.details_link),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Icon(
                            modifier = Modifier
                                .offset(y = 1.dp),
                            painter = painterResource(R.drawable.ic_arrow_right),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
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
        CourseCard(
            imageUrl = "",
            rate = "3.4",
            date = "22 May 2022",
            title = "Title",
            description = "Освойте backend-разработку \nи программирование на Java, фреймворки Spring и Maven, работу" +
                    " с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
            price = "1000",
            onFavoriteClick = {},
            onCardClick = {}
        )
    }
}
