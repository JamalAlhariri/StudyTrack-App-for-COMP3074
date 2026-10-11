package com.example.studytrack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText

class TaskDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_task_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        val taskId = arguments?.getInt("taskId") ?: -1
        val task = TaskStore.getTask(taskId)

        if (task == null) {
            Toast.makeText(requireContext(), "Task not found", Toast.LENGTH_SHORT).show()
            activity.showTaskList()
            return
        }

        val title = view.findViewById<TextInputEditText>(R.id.editDetailTitle)
        val course = view.findViewById<TextInputEditText>(R.id.editDetailCourse)
        val dueDate = view.findViewById<TextInputEditText>(R.id.editDetailDueDate)
        val priority = view.findViewById<Spinner>(R.id.spinnerDetailPriority)
        val completed = view.findViewById<CheckBox>(R.id.checkDetailCompleted)

        val priorities = listOf("Low", "Medium", "High")
        priority.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            priorities
        )

        title.setText(task.title)
        course.setText(task.course)
        dueDate.setText(task.dueDate)
        priority.setSelection(priorities.indexOf(task.priority).coerceAtLeast(0))
        completed.isChecked = task.completed

        view.findViewById<View>(R.id.btnUpdate).setOnClickListener {
            if (title.text.toString().trim().isEmpty()) {
                title.error = "Title is required"
                return@setOnClickListener
            }

            task.title = title.text.toString().trim()
            task.course = course.text.toString().trim()
            task.dueDate = dueDate.text.toString().trim()
            task.priority = priority.selectedItem.toString()
            task.completed = completed.isChecked

            Toast.makeText(requireContext(), "Task updated", Toast.LENGTH_SHORT).show()
            activity.showTaskList()
        }

        view.findViewById<View>(R.id.btnDelete).setOnClickListener {
            TaskStore.deleteTask(task.id)
            Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_SHORT).show()
            activity.showTaskList()
        }

        view.findViewById<View>(R.id.btnBackToTasks).setOnClickListener {
            activity.showTaskList()
        }
    }
}
