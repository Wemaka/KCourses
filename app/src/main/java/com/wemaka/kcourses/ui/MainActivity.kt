package com.wemaka.kcourses.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.wemaka.kcourses.data.repositories.users.AuthRepository
import com.wemaka.kcourses.navigation.BaseRoute
import com.wemaka.kcourses.ui.components.BottomBarItem
import com.wemaka.kcourses.ui.components.BottomNavBar
import com.wemaka.kcourses.ui.navigation.AccountGraph
import com.wemaka.kcourses.ui.navigation.CoursesGraph
import com.wemaka.kcourses.ui.navigation.FavoriteCoursesGraph
import com.wemaka.kcourses.ui.navigation.LoginGraph
import com.wemaka.kcourses.ui.navigation.MainNavHost
import com.wemaka.kcourses.uikit.R
import com.wemaka.kcourses.uikit.theme.KCoursesTheme
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val authRepository: AuthRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val items = listOf(
                BottomBarItem(
                    title = stringResource(R.string.nav_home),
                    iconResId = R.drawable.ic_home,
                    route = CoursesGraph
                ),
                BottomBarItem(
                    title = stringResource(R.string.nav_favorites),
                    iconResId = R.drawable.ic_mark,
                    route = FavoriteCoursesGraph
                ),
                BottomBarItem(
                    title = stringResource(R.string.nav_account),
                    iconResId = R.drawable.ic_person,
                    route = AccountGraph
                )
            )

            val startDist = remember {
                getStartDis()
            }

            KCoursesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0),
                    bottomBar = {
                        BottomNavBar(
                            items = items,
                            navController = navController
                        )
                    }
                ) { innerPadding ->
                    MainNavHost(
                        navController = navController,
                        startDestination = startDist,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun getStartDis(): BaseRoute {
        return runBlocking {
            val isLoggedIn = authRepository.isUserLoggedIn()
            if (isLoggedIn) {
                CoursesGraph
            } else {
                LoginGraph
            }
        }
    }
}
