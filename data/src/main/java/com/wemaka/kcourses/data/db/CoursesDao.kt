package com.wemaka.kcourses.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wemaka.kcourses.data.db.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoursesDao {
    @Query("SELECT * FROM courses ORDER BY courses.publish_date ASC")
    fun getAll(): Flow<List<CourseEntity>>

    @Query("UPDATE courses SET has_like = :hasLike WHERE id = :courseId")
    suspend fun setFavoriteStatus(courseId: Int, hasLike: Boolean)

    @Query("SELECT * FROM courses WHERE courses.id = :courseId")
    suspend fun getCourseById(courseId: Int): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(courses: List<CourseEntity>)
}
