package com.example.calendarapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    lateinit var calendarView: CalendarView
    var selectedDate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
            val date = "$dayOfMonth/${month + 1}/$year"
            Toast.makeText(this, "Wybrana data: $date", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("date", selectedDate)
            startActivity(intent)
        }
    }
}
