package com.example.studytrack

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDate

class AddTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        val title = view.findViewById<TextInputEditText>(R.id.editTitle)
        val course = view.findViewById<TextInputEditText>(R.id.editCourse)
        val dueDate = view.findViewById<TextInputEditText>(R.id.editDueDate)
        val notes = view.findViewById<TextInputEditText>(R.id.editNotes)
        val priority = view.findViewById<Spinner>(R.id.spinnerPriority)

        val priorities = listOf("Low", "Medium", "High")
        priority.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            priorities
        )
        priority.setSelection(1)

        dueDate.setOnClickListener {
            val today = LocalDate.now()
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    dueDate.setText(
                        String.format("%04d-%02d-%02d", year, month + 1, day)
                    )
                },
                today.year,
                today.monthValue - 1,
                today.dayOfMonth
            ).show()
        }

        view.findViewById<View>(R.id.btnSaveTask).setOnClickListener {
            val taskTitle = title.text.toString().trim()
            val taskCourse = course.text.toString().trim()
            val taskDueDate = dueDate.text.toString().trim()

            if (taskTitle.isEmpty() || taskCourse.isEmpty() || taskDueDate.isEmpty()) {
                Toast.makeText(requireContext(), "Enter title, course, and due date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            TaskStore.addTask(
                taskTitle,
                taskCourse,
                taskDueDate,
                priority.selectedItem.toString(),
                notes.text.toString().trim()
            )

            Toast.makeText(requireContext(), "Task saved", Toast.LENGTH_SHORT).show()
            activity.showTaskList()
        }

        view.findViewById<View>(R.id.btnCancel).setOnClickListener {
            activity.showDashboard()
        }
    }
}
