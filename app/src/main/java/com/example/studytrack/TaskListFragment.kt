package com.example.studytrack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class TaskListFragment : Fragment() {

    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerTasks)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        adapter = TaskAdapter(
            TaskStore.tasks.sortedBy { it.dueDate },
            onTaskClick = { task -> activity.showTaskDetails(task.id) },
            onCompletedChange = { task, completed -> task.completed = completed }
        )
        recycler.adapter = adapter

        view.findViewById<View>(R.id.btnAll).setOnClickListener {
            showAll()
        }

        view.findViewById<View>(R.id.btnDueSoon).setOnClickListener {
            val today = LocalDate.now()
            val end = today.plusDays(7)
            val dueSoon = TaskStore.tasks.filter { task ->
                if (task.completed) return@filter false
                try {
                    val date = LocalDate.parse(task.dueDate)
                    !date.isBefore(today) && !date.isAfter(end)
                } catch (_: Exception) {
                    false
                }
            }.sortedBy { it.dueDate }
            adapter.updateItems(dueSoon)
        }

        view.findViewById<View>(R.id.btnCompleted).setOnClickListener {
            adapter.updateItems(TaskStore.tasks.filter { it.completed })
        }

        view.findViewById<View>(R.id.btnAddFromList).setOnClickListener {
            activity.showAddTask()
        }

        view.findViewById<View>(R.id.btnHome).setOnClickListener {
            activity.showDashboard()
        }
    }

    private fun showAll() {
        adapter.updateItems(TaskStore.tasks.sortedBy { it.dueDate })
    }
}
