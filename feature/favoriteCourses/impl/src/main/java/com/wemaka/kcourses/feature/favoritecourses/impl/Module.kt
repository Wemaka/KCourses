package com.wemaka.kcourses.feature.favoritecourses.impl

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val favoriteCoursesImplModule = module {
    viewModelOf(::FavoriteCoursesViewModel)
}