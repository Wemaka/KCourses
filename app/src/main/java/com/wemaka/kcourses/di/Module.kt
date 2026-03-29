package com.wemaka.kcourses.di

import com.wemaka.kcourses.courses.impl.coursesImplModule
import com.wemaka.kcourses.data.di.dataModule
import com.wemaka.kcourses.domain.di.domainModule
import com.wemaka.kcourses.feature.favoritecourses.impl.favoriteCoursesImplModule
import com.wemaka.kcourses.feature.login.impl.loginImplModule
import org.koin.dsl.module

val rootModule = module {
    includes(
        dataModule
    )

    includes(
        domainModule
    )

    includes(
        loginImplModule,
        coursesImplModule,
        favoriteCoursesImplModule
    )
}