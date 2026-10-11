package com.example.studytrack

data class Task(
    val id: Int,
    var title: String,
    var course: String,
    var dueDate: String,
    var priority: String,
    var notes: String = "",
    var completed: Boolean = false
)
