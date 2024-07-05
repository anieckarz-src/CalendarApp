package com.example.calendarapp

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class TaskActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var tasks: MutableList<String>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var formattedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val dateTextView: TextView = findViewById(R.id.dateTextView)
        val taskEditText: EditText = findViewById(R.id.taskEditText)
        val saveButton: Button = findViewById(R.id.saveButton)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        val dateInMillis = intent.getLongExtra("date", 0)
        val date = Date(dateInMillis)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formattedDate = format.format(date)

        dateTextView.text = "Data: $formattedDate"

        sharedPreferences = getSharedPreferences("tasks", MODE_PRIVATE)
        tasks = loadTasks(formattedDate)

        taskAdapter = TaskAdapter(tasks) { position ->
            removeTask(position)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        saveButton.setOnClickListener {
            val task = taskEditText.text.toString()
            if (task.isNotEmpty()) {
                tasks.add(task)
                saveTasks(formattedDate, tasks)
                taskAdapter.notifyDataSetChanged()
                taskEditText.text.clear()
                Toast.makeText(this, "Zadanie zapisane: $task", Toast.LENGTH_SHORT).show()
                finish() // Wraca do widoku kalendarza po zapisaniu zadania
            } else {
                Toast.makeText(this, "Zadanie nie może być puste", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTasks(date: String): MutableList<String> {
        val tasksString = sharedPreferences.getString(date, "")
        return if (tasksString!!.isEmpty()) {
            mutableListOf()
        } else {
            tasksString.split(";").toMutableList()
        }
    }

    private fun saveTasks(date: String, tasks: List<String>) {
        val tasksString = tasks.joinToString(";")
        val editor = sharedPreferences.edit()
        editor.putString(date, tasksString)
        editor.apply()
    }

    private fun removeTask(position: Int) {
        tasks.removeAt(position)
        saveTasks(formattedDate, tasks)
        taskAdapter.notifyItemRemoved(position)
        Toast.makeText(this, "Zadanie usunięte", Toast.LENGTH_SHORT).show()
    }
}
