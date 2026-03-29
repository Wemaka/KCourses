package com.wemaka.kcourses.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wemaka.kcourses.navigation.BaseRoute
import com.wemaka.kcourses.navigation.navigateNewTask
import com.wemaka.kcourses.ui.navigation.AccountGraph
import com.wemaka.kcourses.ui.navigation.CoursesGraph
import com.wemaka.kcourses.ui.navigation.FavoriteCoursesGraph
import com.wemaka.kcourses.uikit.R
import com.wemaka.kcourses.uikit.theme.KCoursesTheme

data class BottomBarItem(
    val title: String,
    @DrawableRes val iconResId: Int,
    val route: BaseRoute
)

@Composable
fun BottomNavBar(
    items: List<BottomBarItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currDestination = navBackStackEntry?.destination
    val selectedRoute = remember(currDestination) {
        items
            .firstOrNull { item ->
                currDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
            }?.route
    }
    val isNavigationBarVisible = remember(selectedRoute) {
        selectedRoute != null
    }
    val outlineColor = MaterialTheme.colorScheme.outline

    AnimatedVisibility(
        visible = isNavigationBarVisible,
        label = "Navigation bar visibility",
        enter = fadeIn() + slideInVertically { it },
        exit = fadeOut() + slideOutVertically { it },
    ) {
        NavigationBar(
            modifier = modifier
                .drawBehind {
                    drawLine(
                        color = outlineColor,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1.dp.toPx()
                    )
                },
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            windowInsets = WindowInsets(0)
        ) {
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
            ) {
                items.forEach {
                    val isSelected = selectedRoute == it.route

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { if (!isSelected) navController.navigateNewTask(it.route, it.route) },
                        icon = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(it.iconResId),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val items = listOf(
        BottomBarItem(
            title = "Главная",
            iconResId = R.drawable.ic_home,
            route = CoursesGraph
        ),
        BottomBarItem(
            title = "Избранное",
            iconResId = R.drawable.ic_mark,
            route = FavoriteCoursesGraph
        ),
        BottomBarItem(
            title = "Аккаунт",
            iconResId = R.drawable.ic_person,
            route = AccountGraph
        )
    )

    KCoursesTheme() {
        BottomNavBar(
            items = items,
            navController = rememberNavController()
        )
    }
}