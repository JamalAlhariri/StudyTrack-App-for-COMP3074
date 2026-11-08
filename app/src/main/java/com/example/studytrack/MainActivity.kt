package com.example.studytrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        TaskStore.load(this) {
            showDashboard()
        }

    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun showDashboard() = openFragment(DashboardFragment())
    fun showAddTask() = openFragment(AddTaskFragment())
    fun showTaskList() = openFragment(TaskListFragment())
    fun showCourses() = openFragment(CoursesFragment())

    fun showTaskDetails(taskId: Int) {
        val fragment = TaskDetailsFragment()
        fragment.arguments = Bundle().apply {
            putInt("taskId", taskId)
        }
        openFragment(fragment)
    }
}
