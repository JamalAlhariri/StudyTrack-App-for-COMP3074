
package com.example.studytrack

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey
    val id: Int,
    var title: String,
    var course: String,
    var dueDate: String,
    var priority: String,
    var notes: String = "",
    var completed: Boolean = false
)
