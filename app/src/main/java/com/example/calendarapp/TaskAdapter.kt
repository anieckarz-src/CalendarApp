package com.example.calendarapp

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val tasks: MutableList<String>, private val onTaskClick: (Int) -> Unit) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view, onTaskClick)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.taskTextView.text = tasks[position]
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder(itemView: View, private val onTaskClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val taskTextView: TextView = itemView.findViewById(R.id.taskTextView)

        init {
            var lastClickTime = 0L
            itemView.setOnClickListener {
                val currentClickTime = SystemClock.elapsedRealtime()
                if (currentClickTime - lastClickTime < 300) {
                    onTaskClick(adapterPosition)
                }
                lastClickTime = currentClickTime
            }
        }
    }
}
