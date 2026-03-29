package com.wemaka.kcourses.domain.di

import com.wemaka.kcourses.domain.auth.LoginUseCase
import com.wemaka.kcourses.domain.courses.GetCoursesUseCase
import com.wemaka.kcourses.domain.courses.SetFavoriteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetCoursesUseCase)
    factoryOf(::SetFavoriteUseCase)
    factoryOf(::LoginUseCase)
}