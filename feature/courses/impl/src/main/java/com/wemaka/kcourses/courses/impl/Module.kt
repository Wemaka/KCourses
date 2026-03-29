package com.wemaka.kcourses.courses.impl

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val coursesImplModule = module {
    viewModelOf(::CoursesViewModel)
}