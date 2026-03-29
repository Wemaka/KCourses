package com.wemaka.kcourses.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wemaka.kcourses.courses.api.CoursesRoute
import com.wemaka.kcourses.courses.impl.CoursesScreen
import com.wemaka.kcourses.feature.favoritecourses.api.FavoriteCoursesRoute
import com.wemaka.kcourses.feature.favoritecourses.impl.FavoriteCoursesScreen
import com.wemaka.kcourses.feature.login.api.LoginRoute
import com.wemaka.kcourses.feature.login.impl.LoginScreen
import com.wemaka.kcourses.navigation.navigateNewTask

@Composable
fun MainNavHost(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        addLoginGraph(navController)
        addCoursesGraph(navController)
        addFavoriteCoursesGraph(navController)
        addAccountGraph(navController)
    }
}

fun NavGraphBuilder.addLoginGraph(
    navController: NavHostController
) {
    navigation<LoginGraph>(
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(
                onSuccessLogin = {
                    navController.navigateNewTask(CoursesGraph, LoginGraph)
                }
            )
        }
    }
}

fun NavGraphBuilder.addCoursesGraph(
    navController: NavHostController
) {
    navigation<CoursesGraph>(
        startDestination = CoursesRoute
    ) {
        composable<CoursesRoute> {
            CoursesScreen(
                navigateToDetail = {}
            )
        }
    }
}

fun NavGraphBuilder.addFavoriteCoursesGraph(
    navController: NavHostController
) {
    navigation<FavoriteCoursesGraph>(
        startDestination = FavoriteCoursesRoute
    ) {
        composable<FavoriteCoursesRoute> {
            FavoriteCoursesScreen(
                navigateToDetail = {}
            )
        }
    }
}

fun NavGraphBuilder.addAccountGraph(
    navController: NavHostController
) {
    composable<AccountGraph> {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Soon",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}