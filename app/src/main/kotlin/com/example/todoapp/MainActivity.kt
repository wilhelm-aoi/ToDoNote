package com.example.todoapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.models.Task

class MainActivity : AppCompatActivity() {

    private lateinit var taskManager: TaskManager
    private lateinit var taskRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskManager = TaskManager(this)
        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)

        loadTasks()
    }

    private fun loadTasks() {
        val tasks = taskManager.getAllTasks()
        taskRecyclerView.adapter = TaskAdapter(tasks)
    }
}