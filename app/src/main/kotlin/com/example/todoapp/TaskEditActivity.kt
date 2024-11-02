package com.example.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.models.Task

class TaskEditActivity : AppCompatActivity() {

    private lateinit var taskManager: TaskManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_edit)

        taskManager = TaskManager(this)
    }

    private fun saveTask(task: Task) {
        taskManager.saveTask(task)
        finish()
    }
}