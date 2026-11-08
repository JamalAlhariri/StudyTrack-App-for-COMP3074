
package com.example.studytrack

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Task::class, Course::class],
    version = 1,
    exportSchema = false
)
abstract class StudyDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
