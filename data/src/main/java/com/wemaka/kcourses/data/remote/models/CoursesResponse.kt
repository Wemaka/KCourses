package com.wemaka.kcourses.data.remote.models

import com.wemaka.kcourses.data.models.courses.Course

data class CoursesResponse(
    val courses: List<Course>
)
