package com.wemaka.kcourses.data.di

import com.wemaka.kcourses.data.db.AppDatabase
import com.wemaka.kcourses.data.db.CoursesDao
import com.wemaka.kcourses.data.db.UsersDao
import com.wemaka.kcourses.data.remote.CoursesApi
import com.wemaka.kcourses.data.remote.CoursesApiImpl
import com.wemaka.kcourses.data.repositories.courses.CoursesRepository
import com.wemaka.kcourses.data.repositories.courses.CoursesRepositoryImpl
import com.wemaka.kcourses.data.repositories.users.AuthRepository
import com.wemaka.kcourses.data.repositories.users.AuthRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<CoursesApi> {
        CoursesApiImpl()
    }

    single {
        AppDatabase.getDatabase(androidContext())
    }

    single<CoursesDao> {
        get<AppDatabase>().coursesDao
    }

    single<UsersDao> {
        get<AppDatabase>().usersDao
    }

    single<CoursesRepository> {
        CoursesRepositoryImpl(get(), get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
}