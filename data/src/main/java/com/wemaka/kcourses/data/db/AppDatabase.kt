package com.wemaka.kcourses.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wemaka.kcourses.data.db.entity.CourseEntity
import com.wemaka.kcourses.data.db.entity.UserEntity
import com.wemaka.kcourses.data.models.courses.toLocal
import com.wemaka.kcourses.data.remote.CoursesApiImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [CourseEntity::class, UserEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val coursesDao: CoursesDao
    abstract val usersDao: UsersDao

    companion object {
        const val DATABASE_NAME = "app_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    // Загрузка данных при первом запуске
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                getDatabase(context)
                                    .coursesDao
                                    .insertAll(
                                        CoursesApiImpl().getCourses().courses.map { it.toLocal() }
                                    )
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}