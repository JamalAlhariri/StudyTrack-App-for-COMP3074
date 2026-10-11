package com.example.studytrack

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private var items: List<Task>,
    private val onTaskClick: (Task) -> Unit,
    private val onCompletedChange: (Task, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkCompleted: CheckBox = view.findViewById(R.id.checkCompleted)
        val title: TextView = view.findViewById(R.id.txtTaskTitle)
        val info: TextView = view.findViewById(R.id.txtTaskInfo)
        val textContainer: View = view.findViewById(R.id.taskTextContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = items[position]

        holder.title.text = task.title
        holder.info.text = "${task.course}  •  ${task.dueDate}  •  ${task.priority}"

        holder.checkCompleted.setOnCheckedChangeListener(null)
        holder.checkCompleted.isChecked = task.completed
        setCompletedStyle(holder.title, task.completed)

        holder.checkCompleted.setOnCheckedChangeListener { _, checked ->
            onCompletedChange(task, checked)
            setCompletedStyle(holder.title, checked)
        }

        holder.textContainer.setOnClickListener {
            onTaskClick(task)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Task>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun setCompletedStyle(textView: TextView, completed: Boolean) {
        textView.paintFlags = if (completed) {
            textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}
