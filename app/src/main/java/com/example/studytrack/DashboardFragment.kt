package com.example.studytrack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        val summary = view.findViewById<TextView>(R.id.txtSummary)
        val upcoming = view.findViewById<LinearLayout>(R.id.upcomingContainer)

        val activeTasks = TaskStore.tasks
            .filter { !it.completed }
            .sortedBy { it.dueDate }

        summary.text = "${activeTasks.size} tasks coming up"
        upcoming.removeAllViews()

        activeTasks.take(3).forEach { task ->
            val item = TextView(requireContext()).apply {
                text = "${task.title}\n${task.course}  •  ${task.dueDate}  •  ${task.priority}"
                textSize = 16f
                setTextColor(resources.getColor(R.color.black, null))
                setBackgroundResource(R.drawable.card_background)
                setPadding(dp(14), dp(12), dp(14), dp(12))
                setOnClickListener { activity.showTaskDetails(task.id) }
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.bottomMargin = dp(10)
            upcoming.addView(item, params)
        }

        if (activeTasks.isEmpty()) {
            upcoming.addView(TextView(requireContext()).apply {
                text = "No upcoming tasks."
                textSize = 16f
            })
        }

        view.findViewById<View>(R.id.btnAddTask).setOnClickListener { activity.showAddTask() }
        view.findViewById<View>(R.id.btnTasks).setOnClickListener { activity.showTaskList() }
        view.findViewById<View>(R.id.btnCourses).setOnClickListener { activity.showCourses() }
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }
}
