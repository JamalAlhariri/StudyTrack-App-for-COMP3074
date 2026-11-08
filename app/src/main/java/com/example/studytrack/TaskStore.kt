
package com.example.studytrack

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import java.util.concurrent.Executors

object TaskStore {

    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var dao: TaskDao
    private var nextId = 1

    val tasks = mutableListOf<Task>()
    val courses = mutableListOf<String>()

    fun load(context: Context, onReady: () -> Unit) {
        executor.execute {
            val database = Room.databaseBuilder(
                context.applicationContext,
                StudyDatabase::class.java,
                "studytrack.db"
            ).build()

            dao = database.taskDao()

            val savedTasks = dao.getAllTasks()
            val savedCourses = dao.getAllCourses()

            Handler(Looper.getMainLooper()).post {
                tasks.clear()
                tasks.addAll(savedTasks)

                courses.clear()
                courses.addAll(savedCourses.map { it.name })

                nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1

                if (courses.isEmpty()) {
                    addCourse("COMP3074")
                    addCourse("COMP3122")
                    addCourse("COMP3104")
                }

                onReady()
            }
        }
    }

    fun addTask(
        title: String,
        course: String,
        dueDate: String,
        priority: String,
        notes: String
    ) {
        val task = Task(
            nextId++,
            title,
            course,
            dueDate,
            priority,
            notes
        )

        tasks.add(task)
        addCourse(course)

        executor.execute {
            dao.saveTask(task)
        }
    }

    fun getTask(id: Int): Task? {
        return tasks.find { it.id == id }
    }

    fun updateTask(task: Task) {
        val savedTask = task.copy()
        addCourse(savedTask.course)

        executor.execute {
            dao.saveTask(savedTask)
        }
    }

    fun deleteTask(id: Int) {
        tasks.removeAll { it.id == id }

        executor.execute {
            dao.deleteTask(id)
        }
    }

    fun addCourse(name: String) {
        val courseName = name.trim()

        if (courseName.isNotEmpty() && !courses.contains(courseName)) {
            courses.add(courseName)

            executor.execute {
                dao.saveCourse(Course(courseName))
            }
        }
    }
}
