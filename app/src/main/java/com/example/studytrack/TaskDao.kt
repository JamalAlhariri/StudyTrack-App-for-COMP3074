
package com.example.studytrack

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTask(task: Task)

    @Query("DELETE FROM tasks WHERE id = :id")
    fun deleteTask(id: Int)

    @Query("SELECT * FROM courses")
    fun getAllCourses(): List<Course>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCourse(course: Course)
}
