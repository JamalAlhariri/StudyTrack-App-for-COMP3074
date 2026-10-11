package com.example.studytrack

import java.time.LocalDate

object TaskStore {
    private var nextId = 1

    val tasks = mutableListOf<Task>()
    val courses = mutableListOf("COMP3074", "COMP3122", "COMP3104")

    init {
        val today = LocalDate.now()
        addTask("UI Prototype", "COMP3074", today.plusDays(2).toString(), "High", "Finish the mobile UI prototype")

    }

    fun addTask(title: String, course: String, dueDate: String, priority: String, notes: String) {
        tasks.add(Task(nextId++, title, course, dueDate, priority, notes))
        if (course.isNotBlank() && !courses.contains(course)) {
            courses.add(course)
        }
    }

    fun getTask(id: Int): Task? = tasks.find { it.id == id }

    fun deleteTask(id: Int) {
        tasks.removeAll { it.id == id }
    }
}
