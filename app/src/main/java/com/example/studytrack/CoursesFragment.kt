package com.example.studytrack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText

class CoursesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_courses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        val courseName = view.findViewById<TextInputEditText>(R.id.editCourseName)
        val courseContainer = view.findViewById<LinearLayout>(R.id.courseContainer)

        fun refreshCourses() {
            courseContainer.removeAllViews()
            TaskStore.courses.forEach { course ->
                val text = TextView(requireContext()).apply {
                    this.text = course
                    textSize = 17f
                    setBackgroundResource(R.drawable.card_background)
                    setPadding(dp(14), dp(14), dp(14), dp(14))
                }
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.bottomMargin = dp(10)
                courseContainer.addView(text, params)
            }
        }

        refreshCourses()

        view.findViewById<View>(R.id.btnAddCourse).setOnClickListener {
            val name = courseName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Enter a course name", Toast.LENGTH_SHORT).show()
            } else {

                TaskStore.addCourse(name)

                courseName.setText("")
                refreshCourses()
            }
        }

        view.findViewById<View>(R.id.btnCoursesHome).setOnClickListener {
            activity.showDashboard()
        }
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }
}
