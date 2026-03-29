package com.wemaka.kcourses.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wemaka.kcourses.data.models.courses.Course

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    @ColumnInfo("start_date")
    val startDate: String,
    @ColumnInfo("has_like")
    val hasLike: Boolean,
    @ColumnInfo("publish_date")
    val publishDate: String
)

fun CourseEntity.toExternal(): Course = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = publishDate
)

fun List<CourseEntity>.toExternal(): List<Course> {
    return map(CourseEntity::toExternal)
}
